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
import site.nomoreparties.stellarburgers.constant.Message;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.InfoResponse;
import site.nomoreparties.stellarburgers.model.User;

import static site.nomoreparties.stellarburgers.model.generator.UserGenerator.*;

@RunWith(Parameterized.class)
@DisplayName("Тест \"логин с неверным логином и паролем\"")
@RequiredArgsConstructor
public class LoginRequiredFieldErrorTest {

    private UserClient userClient;
    private String accessToken;
    private User userLogin;

    private final String email;
    private final String password;
    private final Boolean isNull;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                {null, createPassword(), false},
                {createEmail(), null, false},
                {null, null, false},
                {null, createPassword(), true},
                {createEmail(), null, true},
                {null, null, true}
        };
    }

    private User createLoginWrongUser() {
        if (isNull) {
            return new User(email, password, null);
        } else {
            return new User(
                    email != null ? email : createEmail(),
                    password != null ? password : createPassword(),
                    null
            );
        }
    }

    @Before
    public void initClientAndUsers() {
        userClient = new UserClient();
        User userRegister = new User(
                email != null ? email : createEmail(),
                password != null ? password : createPassword(),
                createName()
        );
        accessToken = userClient.register(userRegister, 200).as(AuthUser.class).getAccessToken();
        userLogin = createLoginWrongUser();
    }

    @After
    public void deleteUser() {
        userClient.delete(accessToken, 202);
    }

    @Test
    @DisplayName("Тест \"логин под существующим пользователем\"")
    @Description("Авторизуемся -> сравниваем тело ответа с ожидаемым")
    public void loginSuccessTest() {
        Response response = userClient.login(userLogin, 401);
        userClient.compareResponseBody(
                new InfoResponse(
                        false,
                        Message.LOGIN_INCORRECT_FIELDS_ERROR),
                response,
                InfoResponse.class
        );
    }
}
