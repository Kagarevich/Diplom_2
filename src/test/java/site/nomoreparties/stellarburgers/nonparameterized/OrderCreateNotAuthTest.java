package site.nomoreparties.stellarburgers.nonparameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.OrderClient;
import site.nomoreparties.stellarburgers.constant.Message;
import site.nomoreparties.stellarburgers.model.InfoResponse;
import site.nomoreparties.stellarburgers.model.IngredientHashes;

@DisplayName("Тест \"Создание заказа без авторизации\"")
public class OrderCreateNotAuthTest {

    private OrderClient orderClient;
    private IngredientHashes ingredientHashes;

    @Before
    public void initClientAndIngredients() {
        orderClient = new OrderClient();
        ingredientHashes = orderClient.getTwoIngredientsHash(200);
    }

    @Test
    @DisplayName("Тест \"Создание заказа без авторизации\" \"с ингредиентами\"")
    @Description("Создание заказа с ингредиентами -> мэтчим тело ответа и сравниваем часть тела ответа с ожидаемым")
    public void orderCreateNotAuthTest() {
        Response response = orderClient.create(
                ingredientHashes,
                200
        );
        orderClient.compareAuthOrderCreateResponseBody(response);
    }

    @Test
    @DisplayName("Тест \"Создание заказа без авторизации\" \"без ингредиентов\"")
    @Description("Создание заказа без ингредиентов -> мэтчим тело ответа и сравниваем часть тела ответа с ожидаемым")
    public void orderCreateNotAuthNoIngredientErrorTest() {
        Response response = orderClient.create(
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
    @DisplayName("Тест \"Создание заказа без авторизации\" \"с неверным хешем ингредиентов\"")
    @Description("Создание заказа с неверным хешем ингредиентов")
    public void orderCreateNotAuthWrongIngredientHashErrorTest() {
        orderClient.create(
                new IngredientHashes(new String[] {"a0a0465"}),
                500
        );
    }
}
