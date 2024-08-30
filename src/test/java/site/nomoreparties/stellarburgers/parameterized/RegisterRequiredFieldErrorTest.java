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
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

@RunWith(Parameterized.class)
@DisplayName("Тест \"создать пользователя и не заполнить одно из обязательных полей\"")
@RequiredArgsConstructor
public class RegisterRequiredFieldErrorTest {

    private UserClient userClient;
    private Response response;

    private final User user;
    private final int expectedStatusCode;

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                {new User(
                        null,
                        UserGenerator.createPassword(),
                        UserGenerator.createName()
                ), 403},
                {new User(
                        UserGenerator.createEmail(),
                        null,
                        UserGenerator.createName()
                ), 403},
                {new User(
                        UserGenerator.createEmail(),
                        UserGenerator.createPassword(),
                        null
                ), 403},
                {new User(
                        UserGenerator.createEmail(),
                        null,
                        null
                ), 403},
                {new User(
                        null,
                        null,
                        UserGenerator.createName()
                ), 403},
                {new User(
                        null,
                        UserGenerator.createPassword(),
                        null
                ), 403},
                {new User(
                        null,
                        null,
                        null
                ), 403},
        };
    }

    @Before
    public void initUserAndUserClient() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Тестирование 403 ошибки при недостатке данных при регистрации")
    @Description("Тестирование 403 ошибки регистрации: " +
            "сравнение статус кодов при вызове запроса -> " +
            "сравнение тела ответа с ожидаемым")
    public void registerTest() {
        response = userClient.register(user, expectedStatusCode);
        userClient.compareResponseBody(
                new InfoResponse(
                        false,
                        Message.USER_REQUIRED_FIELDS_ERROR),
                response,
                InfoResponse.class
        );
    }

    @After
    @Description("Если пользователь всё-таки создался, то удаляем его")
    public void deleteUser() {
        if (response.statusCode() == 200) {
            userClient.delete(
                    response.as(AuthUser.class).getAccessToken(),
                    202);
        }
    }
}