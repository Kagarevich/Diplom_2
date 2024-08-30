package site.nomoreparties.stellarburgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.constant.PathAPI;
import site.nomoreparties.stellarburgers.model.Ingredient;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient extends BaseClass {

    @Step("Авторизованный пользователь. Создание заказа")
    public Response create(String accessToken, Ingredient ingredients, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(accessToken, JSON))
                .body(ingredients)
                .post(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Неавторизованный пользователь. Создание заказа")
    public Response create(Ingredient ingredients, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(JSON))
                .body(ingredients)
                .post(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Авторизованный пользователь. Получение заказов конкретного пользователя")
    public Response getUserOrdersList(String accessToken, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(accessToken))
                .get(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Неавторизованный пользователь. Получение заказов конкретного пользователя")
    public Response getUserOrdersList(int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
                .get(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Получение списка из двух ингредиентов")
    public Ingredient getTwoIngredients(int expectedStatusCode) {
        Response response = given()
                .spec(ReqSpec.getReqSpec())
                .get(PathAPI.INGREDIENTS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
        return new Ingredient(
                new String[] {
                        response.body().path("data[0]._id"),
                        response.body().path("data[1]._id")
                });
    }
}
