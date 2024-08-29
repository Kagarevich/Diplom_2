package site.nomoreparties.stellarburgers.model;

import lombok.Getter;

@Getter
public class Order {

    private Ingredient[] ingredients;
    private String _id;
    private String status;
    private String number;
    private String createdAt;
    private String updatedAt;

    public Order(Ingredient[] ingredients,
                 String _id,
                 String status,
                 String number,
                 String createdAt,
                 String updatedAt) {
        this.ingredients = ingredients;
        this._id = _id;
        this.status = status;
        this.number = number;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(String number) {
        this.number = number;
    }

    public Order() {
    }
}
