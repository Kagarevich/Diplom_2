package site.nomoreparties.stellarburgers.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser {

    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public AuthUser(boolean success, User user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AuthUser(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public AuthUser() {}
}
