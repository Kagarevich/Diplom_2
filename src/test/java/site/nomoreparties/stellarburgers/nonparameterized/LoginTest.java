package site.nomoreparties.stellarburgers.nonparameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.UserClient;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

@DisplayName("Тест \"логин\"")
public class LoginTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void initClientAndToken() {
        userClient = new UserClient();
        user = UserGenerator.create();
        accessToken = userClient.register(user, 200).as(AuthUser.class).getAccessToken();
    }

    @After
    public void deleteUser() {
        userClient.delete(accessToken, 202);
    }

    @Test
    @DisplayName("Тест \"логин под существующим пользователем\"")
    @Description("Авторизуемся -> сравниваем тело ответа с ожидаемым")
    public void loginSuccessTest() {
        Response response = userClient.login(user, 200);
        userClient.compareAuthUserResponseBody(
                response,
                new User(
                        user.getEmail(),
                        user.getName()
                )
        );
    }
}
