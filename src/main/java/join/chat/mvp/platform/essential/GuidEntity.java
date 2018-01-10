package join.chat.mvp.platform.essential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class GuidEntity {
    @NotNull
    private final String guid;

    @JsonCreator
    public GuidEntity(@JsonProperty("guid") String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }
}
