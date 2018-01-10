package join.chat.mvp.platform.service;

import join.chat.mvp.platform.model.Validation;
import join.chat.mvp.platform.repository.ValidationRepository;
import join.chat.mvp.platform.service.tool.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final ShortMessageService shortMessageService;
    private final ValidationRepository validationRepository;

    @Autowired
    public VerificationServiceImpl(final ShortMessageService shortMessageService,
                                   final ValidationRepository validationRepository) {
        this.shortMessageService = shortMessageService;
        this.validationRepository = validationRepository;
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
}
