package join.chat.mvp.platform;

public enum WebSecurityRole {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
