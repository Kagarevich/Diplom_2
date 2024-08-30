package site.nomoreparties.stellarburgers.nonparameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.UserClient;
import site.nomoreparties.stellarburgers.constant.Message;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.InfoResponse;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

import java.util.ArrayList;
import java.util.List;

public class RegisterTest {

    private UserClient userClient;
    private User user;
    private List<Response> responses;

    @Before
    public void init() {
        userClient = new UserClient();
        user = UserGenerator.create();
        responses = new ArrayList<>();
    }

    @After
    @Description("Очищение списка пользователей, в том числе и ошибочно созданных")
    public void deleteUser() {
        if (responses.size() != 0) {
            for (Response response : responses) {
                if (response.statusCode() == 200) {
                    userClient.delete(
                            response.as(AuthUser.class).getAccessToken(),
                            202
                    );
                }
            }
        }
    }

    @Test
    @DisplayName("Тест \"создать уникального пользователя\"")
    @Description("Создание -> " +
            "сравнение через мэтчинг данных всего тела ответа -> " +
            "сравнение части тела ответа с ожидаемым")
    public void registerSuccessTest() {
        responses.add(userClient.register(user, 200));
        userClient.compareRegisterResponseBody(
                responses.get(0),
                new User(
                        user.getEmail(),
                        user.getName()
                )
        );
    }

    @Test
    @DisplayName("Тест \"создать пользователя, который уже зарегистрирован\"")
    @Description("Создаем юзера -> пытаемся создать такого же юзера -> смотрит сообщение об ошибке")
    public void registerSameUserErrorTest() {
        responses.add(userClient.register(user, 200));
        responses.add(userClient.register(user, 403));
        userClient.compareResponseBody(
                new InfoResponse(
                        false,
                        Message.USER_ALREADY_EXISTS_ERROR
                ),
                responses.get(1),
                InfoResponse.class
        );
    }
}
