package site.nomoreparties.stellarburgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.constant.PathAPI;
import site.nomoreparties.stellarburgers.model.User;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClass {

    @Step("Регистрация пользователя")
    public Response register(User user, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
                .body(user)
                .post(PathAPI.REGISTER_USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Удаление пользователя")
    public void delete(String accessToken, int expectedStatusCode) {
        given()
                .spec(ReqSpec.getReqSpec())
                .headers("Authorization", accessToken)
                .delete(PathAPI.USER)
                .then()
                .statusCode(expectedStatusCode);
    }

    @Step("Логин пользователя")
    public Response login(User user, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
                .body(user)
                .post(PathAPI.LOGIN_USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Изменение данных авторизованного пользователя")
    public Response update(String accessToken, User user, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
                .headers("Authorization", accessToken)
                .body(user)
                .patch(PathAPI.USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Изменение данных неавторизованного пользователя")
    public Response update(User user, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
                .body(user)
                .patch(PathAPI.USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }
}
