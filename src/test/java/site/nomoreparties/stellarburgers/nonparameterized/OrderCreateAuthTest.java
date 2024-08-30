package site.nomoreparties.stellarburgers.nonparameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.OrderClient;
import site.nomoreparties.stellarburgers.api.client.UserClient;
import site.nomoreparties.stellarburgers.constant.Message;
import site.nomoreparties.stellarburgers.model.*;
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

@DisplayName("Тест \"Создание заказа с авторизацией\"")
public class OrderCreateAuthTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private String accessToken;
    private IngredientHashes ingredientHashes;

    @Before
    public void initClientsTokenAndIngredients() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        User user = UserGenerator.create();
        accessToken = userClient.register(user, 200).as(AuthUser.class).getAccessToken();
        ingredientHashes = orderClient.getTwoIngredientsHash(200);
    }

    @After
    public void deleteUser() {
        userClient.delete(accessToken, 202);
    }

    @Test
    @DisplayName("Тест \"Создание заказа с авторизацией\" \"с ингредиентами\"")
    @Description("Создание заказа с ингредиентами -> мэтчим тело ответа и сравниваем часть тела ответа с ожидаемым")
    public void orderCreateAuthTest() {
        Response response = orderClient.create(
                accessToken,
                ingredientHashes,
                200
        );
        orderClient.compareAuthOrderCreateResponseBody(response);
    }

    @Test
    @DisplayName("Тест \"Создание заказа с авторизацией\" \"без ингредиентов\"")
    @Description("Создание заказа без ингредиентов -> мэтчим тело ответа и сравниваем часть тела ответа с ожидаемым")
    public void orderCreateAuthNoIngredientErrorTest() {
        Response response = orderClient.create(
                accessToken,
                new IngredientHashes(null),
                400
        );
        orderClient.compareResponseBody(new InfoResponse(
                        false,
                        Message.INGREDIENT_IDS_ERROR),
                response,
                InfoResponse.class
        );
    }

    @Test
    @DisplayName("Тест \"Создание заказа с авторизацией\" \"с неверным хешем ингредиентов\"")
    @Description("Создание заказа с неверным хешем ингредиентов")
    public void orderCreateAuthWrongIngredientHashErrorTest() {
        orderClient.create(
                accessToken,
                new IngredientHashes(new String[] {"a0a0465"}),
                500
        );
    }
}
