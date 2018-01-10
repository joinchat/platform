package join.chat.mvp.platform.service;

import join.chat.mvp.platform.service.tool.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final ShortMessageService shortMessageService;

    @Autowired
    public VerificationServiceImpl(final ShortMessageService shortMessageService) {
        this.shortMessageService = shortMessageService;
    }

    private static String generateVerificationCode() {
        return new RandomString(0x06, ThreadLocalRandom.current()).nextString();
    }

    @Override
    public void pushVerificationCode(final String number, final Locale locale) throws InterruptedException {
        shortMessageService.pushVerificationCode(number, generateVerificationCode(), locale);
    }
}
