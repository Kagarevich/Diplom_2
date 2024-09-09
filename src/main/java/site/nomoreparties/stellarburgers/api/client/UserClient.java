package site.nomoreparties.stellarburgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;
import site.nomoreparties.stellarburgers.constant.PathAPI;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.User;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static site.nomoreparties.stellarburgers.model.generator.UserGenerator.*;

public class UserClient extends BaseClass {

    @Step("Регистрация пользователя")
    public Response register(User user, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(JSON))
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
                .spec(ReqSpec.getReqSpec(accessToken))
                .delete(PathAPI.USER)
                .then()
                .statusCode(expectedStatusCode);
    }

    @Step("Логин пользователя")
    public Response login(User user, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(JSON))
                .body(user)
                .post(PathAPI.LOGIN_USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Изменение данных авторизованного пользователя")
    public Response update(
            String accessToken,
            User user,
            int expectedStatusCode,
            Boolean isEmail,
            Boolean isPassword,
            Boolean isName) {
        if (isEmail) user.setEmail(createEmail());
        if (isPassword) user.setPassword(createPassword());
        if (isName) user.setName(createName());
        return given()
                .spec(ReqSpec.getReqSpec(accessToken, JSON))
                .body(user)
                .patch(PathAPI.USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Изменение данных неавторизованного пользователя")
    public Response update(
            User user,
            int expectedStatusCode,
            Boolean isEmail,
            Boolean isPassword,
            Boolean isName) {
        if (isEmail) user.setEmail(createEmail());
        if (isPassword) user.setPassword(createPassword());
        if (isName) user.setName(createName());
        return given()
                .spec(ReqSpec.getReqSpec(JSON))
                .body(user)
                .patch(PathAPI.USER)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Сравнение тела ответа успешной регистрации")
    public void compareAuthUserResponseBody(Response resp, User expectedUser) {
        matchResponseBody(resp, AuthUser.class);
        Assert.assertTrue("success != true", resp.as(AuthUser.class).getSuccess());
        Assert.assertEquals(
                "email/name не совпадают с ожидаемыми",
                expectedUser.toString(),
                resp.as(AuthUser.class).getUser().toString());
    }
}
