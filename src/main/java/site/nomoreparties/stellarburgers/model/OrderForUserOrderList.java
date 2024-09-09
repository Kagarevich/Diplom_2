package site.nomoreparties.stellarburgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderForUserOrderList {

    private String[] ingredients;
    private String _id;
    private String status;
    private Integer number;
    private String createdAt;
    private String updatedAt;
    private String name;
}
