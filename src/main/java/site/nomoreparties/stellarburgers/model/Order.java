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
    private Integer number;
    private String createdAt;
    private String updatedAt;
    private Owner owner;
    private String name;
    private Integer price;


    public Order(Integer number) {
        this.number = number;
    }
}
