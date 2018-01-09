package join.chat.mvp.platform.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Find your Account Sid and Token at twilio.com/user/account

@Configuration
@ConfigurationProperties(prefix = "join.chat.configuration.twilio")
public class TwilioConfiguration {
    /**
     * Authorization Token.
     */
    private String authToken;

    /**
     * Account SID.
     */
    private String accountSid;

    /**
     * SMS phone numebr.
     */
    private String phoneNumber;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(final String token) {
        authToken = token;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(final String sid) {
        accountSid = sid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String number) {
        phoneNumber = number;
    }
}
