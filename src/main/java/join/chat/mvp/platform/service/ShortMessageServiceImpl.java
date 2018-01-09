package join.chat.mvp.platform.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import join.chat.mvp.platform.configuration.TwilioConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Service
public class ShortMessageServiceImpl implements ShortMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ShortMessageServiceImpl.class);

    private final MessageSource messageSource;
    private final TwilioConfiguration configuration;

    @Autowired
    public ShortMessageServiceImpl(final MessageSource messageSource, final TwilioConfiguration configuration) {

        this.messageSource = messageSource;
        this.configuration = configuration;

        Twilio.init(this.configuration.getAccountSid(), this.configuration.getAuthToken());
        logger.info("Twilio initialized with SID: {0} and token: {1}",
                this.configuration.getAccountSid(), this.configuration.getAuthToken());
    }

    @Async
    @Override
    public CompletableFuture<String> pushVerificationCode(final String number, final String code)
            throws InterruptedException {
        return pushVerificationCode(number, code, null);
    }

    @Async
    @Override
    public CompletableFuture<String> pushVerificationCode(final String number, final String code, final Locale locale)
            throws InterruptedException {
        final String phoneFrom = this.configuration.getPhoneNumber();
        final Message message = Message.creator(new PhoneNumber(number), new PhoneNumber(phoneFrom),
                code + " " + messageSource.getMessage("validation_code", null, locale)).create();

        logger.info("Sending verification code: {0} to {1} from {2}", code, number, phoneFrom);
        return CompletableFuture.completedFuture(message.getSid());
    }
}