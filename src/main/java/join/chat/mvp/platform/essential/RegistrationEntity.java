package join.chat.mvp.platform.essential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class RegistrationEntity {
    @NotNull
    private final String phone;
    @NotNull
    private final String username;
    @NotNull
    private final String password;

    @JsonCreator
    public RegistrationEntity(@JsonProperty(value = "phone", required = true) String phone,
                              @JsonProperty(value = "username", required = true) String username,
                              @JsonProperty(value = "password", required = true) String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
