package join.chat.mvp.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final ShortMessageService shortMessageService;

    @Autowired
    public VerificationServiceImpl(final ShortMessageService shortMessageService) {
        this.shortMessageService = shortMessageService;
    }

    @Override
    public String pushVerificationCode(final String number, final Locale locale) {
        final String code = null;

        try {
            CompletableFuture<String> messageId = shortMessageService.pushVerificationCode(number, code, locale);
            return messageId.get();
        } catch (ExecutionException | InterruptedException ignored) {
        }
        throw new InternalError();
    }
}
