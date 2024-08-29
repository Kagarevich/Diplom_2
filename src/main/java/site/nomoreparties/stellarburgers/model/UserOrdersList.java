package site.nomoreparties.stellarburgers.model;

import lombok.Getter;

@Getter
public class UserOrdersList {

    private boolean success;
    private Order[] orders;
    private int total;
    private int totalToday;

    public UserOrdersList(boolean success,
                          Order[] orders,
                          int total,
                          int totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public UserOrdersList() {
    }
}
