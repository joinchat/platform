package join.chat.mvp.platform.service;

import join.chat.mvp.platform.configuration.ValidationConfiguration;
import join.chat.mvp.platform.model.Validation;
import join.chat.mvp.platform.repository.ValidationRepository;
import join.chat.mvp.platform.service.tool.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final ShortMessageService shortMessageService;
    private final ValidationRepository validationRepository;
    private final ValidationConfiguration validationConfiguration;

    @Autowired
    public VerificationServiceImpl(final ShortMessageService shortMessageService,
                                   final ValidationRepository validationRepository,
                                   final ValidationConfiguration validationConfiguration) {
        this.shortMessageService = shortMessageService;
        this.validationRepository = validationRepository;
        this.validationConfiguration = validationConfiguration;
    }

    private static String generateVerificationCode() {
        return new RandomString(0x06, ThreadLocalRandom.current()).nextString();
    }

    @Override
    @Transactional
    public void pushVerificationCode(final String number, final Locale locale) throws InterruptedException {
        final String code = generateVerificationCode();
        final Validation validation = validationRepository.findByNumber(number);

        if (validation != null) validationRepository.delete(validation);
        validationRepository.saveAndFlush(new Validation(code, number));

        shortMessageService.pushVerificationCode(number, code, locale);
    }

    @Override
    public Validation verifyVerificationCode(final String code, final String number) {
        final Validation validation = validationRepository.findByNumber(number);
        if (validation == null || !Objects.equals(validation.getCode(), code)) {
            return null;
        }

        final Long expirationTime = validation.getCreatedAt() +
                validationConfiguration.getAuthTokenTimeout() * 1000;
        if (System.currentTimeMillis() > expirationTime) {
            return null;
        }

        // Update current time for temporary token
        validation.setCreatedAt(System.currentTimeMillis());
        validationRepository.saveAndFlush(validation);
        return validation;
    }
}
