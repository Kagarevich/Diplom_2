package site.nomoreparties.stellarburgers.model;

import lombok.Getter;

@Getter
public class OrderCreateResponse {

    private String name;
    private Order order;
    private boolean success;

    public OrderCreateResponse(String name,
                               Order order,
                               boolean success) {
        this.name = name;
        this.order = order;
        this.success = success;
    }

    public OrderCreateResponse() {
    }
}
