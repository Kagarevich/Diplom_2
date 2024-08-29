package site.nomoreparties.stellarburgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.model.Ingredient;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClass {

    /*@Step("Создание заказа")
    public Response create(Ingredient[] ingredients, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
    }*/
}
