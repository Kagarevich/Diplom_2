package site.nomoreparties.stellarburgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Ingredient[] ingredients;
    private String _id;
    private String status;
    private String number;
    private String createdAt;
    private String updatedAt;

    public Order(String number) {
        this.number = number;
    }
}
