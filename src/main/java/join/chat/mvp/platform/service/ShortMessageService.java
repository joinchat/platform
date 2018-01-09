package join.chat.mvp.platform.service;

import org.springframework.scheduling.annotation.Async;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface ShortMessageService {
    @Async
    CompletableFuture<String> pushVerificationCode(final String number, final String code)
            throws InterruptedException;

    @Async
    CompletableFuture<String> pushVerificationCode(final String number, final String code, final Locale locale)
            throws InterruptedException;
}
