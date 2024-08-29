package site.nomoreparties.stellarburgers.model;

import lombok.*;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Ingredient {

    private String hash;

    public Ingredient(String hash) {
        this.hash = hash;
    }

    public Ingredient() {
    }
}
