package join.chat.mvp.platform.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import join.chat.mvp.platform.configuration.TwilioConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TwilioService {
    private static final Logger logger = LoggerFactory.getLogger(TwilioService.class);
    private final TwilioConfiguration configuration;

    @Autowired
    public TwilioService(final TwilioConfiguration configuration) {
        this.configuration = configuration;
        Twilio.init(this.configuration.getAccountSid(), this.configuration.getAuthToken());
    }

    @Async
    public CompletableFuture<String> getVerificationCode(final String number, final String code)
            throws InterruptedException {
        final String phoneFrom = this.configuration.getPhoneNumber();
        final Message message = Message.creator(new PhoneNumber(number), new PhoneNumber(phoneFrom),
                code + " is your Join.Chat verification code").create();

        logger.info("Sending verification code: {0} to {1} from {2}", code, number, phoneFrom);
        return CompletableFuture.completedFuture(message.getSid());
    }
}
