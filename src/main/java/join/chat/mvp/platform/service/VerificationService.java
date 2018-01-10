package join.chat.mvp.platform.service;

import join.chat.mvp.platform.model.Validation;

import java.util.Locale;

public interface VerificationService {
    void pushVerificationCode(final String number, final Locale locale) throws InterruptedException;

    Validation verifyVerificationCode(final String code, final String number);
}
