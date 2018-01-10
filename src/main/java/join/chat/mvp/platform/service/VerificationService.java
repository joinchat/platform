package join.chat.mvp.platform.service;

import java.util.Locale;

public interface VerificationService {
    void pushVerificationCode(String number, final Locale locale) throws InterruptedException;
}
