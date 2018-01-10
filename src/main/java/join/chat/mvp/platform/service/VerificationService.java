package join.chat.mvp.platform.service;

import java.util.Locale;

public interface VerificationService {
    void pushVerificationCode(final String number, final Locale locale) throws InterruptedException;
    boolean verifyVerificationCode(final String code, final String number);
}
