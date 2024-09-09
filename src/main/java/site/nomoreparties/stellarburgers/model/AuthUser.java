package site.nomoreparties.stellarburgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {

    private Boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public AuthUser(boolean success, User user) {
        this.success = success;
        this.user = user;
    }
}
