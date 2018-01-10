package join.chat.mvp.platform.essential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ValidationEntity {
    @NotNull
    @Size(min = 1, max = 8, message = "Validation code must have \\{{min}\\} \\ \\{{max}\\} characters")
    private final String code;

    @NotNull
    @Size(min = 4, max = 15, message = "Phone must have \\{{min}\\} \\ \\{{max}\\} characters")
    private final String phone;

    @JsonCreator
    // TODO: Add validation phone according to the format E.164
    public ValidationEntity(@JsonProperty(value = "code", required = true) String code,
                            @JsonProperty(value = "phone", required = true) String phone) {
        this.code = code;
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }
}
