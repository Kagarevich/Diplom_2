package site.nomoreparties.stellarburgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrdersList {

    private Boolean success;
    private OrderForUserOrderList[] orders;
    private Integer total;
    private Integer totalToday;
}
