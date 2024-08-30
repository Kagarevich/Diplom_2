package site.nomoreparties.stellarburgers.parameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.api.client.UserClient;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

@DisplayName("Тест \"Изменение данных пользователя с авторизации\"")
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class UpdateUserAnyFieldTest {

    private UserClient userClient;
    private User user;
    private String accessToken;

    private final Boolean isEmail;
    private final Boolean isPassword;
    private final Boolean isName;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                {true, false, false},
                {false, true, false},
                {false, false, true},
                {true, false, true},
                {true, true, false},
                {false, true, true},
                {true, true, true}
        };
    }


    @Before
    @Description("регистрируемся и достаём accessToken")
    public void init() {
        userClient = new UserClient();
        user = UserGenerator.create();
        accessToken = userClient.register(user, 200).as(AuthUser.class).getAccessToken();
    }

    @After
    public void deleteUser() {
        userClient.delete(accessToken, 202);
    }

    @Test
    @DisplayName("Тест \"Изменение данных пользователя с авторизацией\"")
    @Description("Изменяем данные пользователя -> сравниваем тело ответа с ожидаемым")
    public void updateUserSuccessTest() {
        Response response = userClient.update(
                accessToken,
                user,
                200,
                isEmail,
                isPassword,
                isName);
        userClient.compareResponseBody(
                new AuthUser(
                        true,
                        new User(
                                user.getEmail(),
                                user.getName()
                        )
                ),
                response,
                AuthUser.class
        );
    }
}
