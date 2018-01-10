package join.chat.mvp.platform.essential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class LoginEntity {
    @NotNull
    private final String username;
    @NotNull
    private final String password;

    @JsonCreator
    public LoginEntity(@JsonProperty("username") String username,
                       @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
