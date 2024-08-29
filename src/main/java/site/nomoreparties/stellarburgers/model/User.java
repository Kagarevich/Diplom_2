package site.nomoreparties.stellarburgers.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
    public User() {}

    @Override
    public String toString() {
        return String.join(" | ", email, password, name);
    }
}
