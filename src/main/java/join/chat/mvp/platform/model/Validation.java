package join.chat.mvp.platform.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 8)
    private String code;

    @Column(name = "guid", length = 128)
    private String guid;

    @Column(name = "number", length = 15)
    private String number;

    @Column(name = "created_at")
    private Long createdAt;

    public Validation() {
    }

    public Validation(final String code, final String number) {
        this.code = code;
        this.number = number;
        this.guid = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
