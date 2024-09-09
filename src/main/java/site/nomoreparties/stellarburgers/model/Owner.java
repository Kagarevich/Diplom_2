package site.nomoreparties.stellarburgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;
}
