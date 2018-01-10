package join.chat.mvp.platform.essential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationEntity {
    @NotNull
    @Size(min = 1, max = 32, message = "Username must have \\{{min}\\} \\ \\{{max}\\} characters")
    private final String username;
    @NotNull
    @Size(min = 1, max = 32, message = "Password must have \\{{min}\\} \\ \\{{max}\\} characters")
    private final String password;

    @JsonCreator
    public RegistrationEntity(@JsonProperty(value = "username", required = true) String username,
                              @JsonProperty(value = "password", required = true) String password) {
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
