package join.chat.mvp.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TwilioService {
    private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);

    @Async
    public CompletableFuture<String> getVerificationCode(String number) throws InterruptedException {
        return CompletableFuture.completedFuture("");
    }
}
