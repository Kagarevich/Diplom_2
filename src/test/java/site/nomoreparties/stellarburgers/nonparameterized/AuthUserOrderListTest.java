package site.nomoreparties.stellarburgers.nonparameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.OrderClient;
import site.nomoreparties.stellarburgers.api.client.UserClient;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.IngredientHashes;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

@DisplayName("Тест \"Получение заказов конкретного пользователя\" \"авторизованный пользователь\"")
public class AuthUserOrderListTest {

    private UserClient userClient;
    private OrderClient orderClient;
    private String accessToken;

    @Before
    public void initClientsTokenAndIngredients() {
        userClient = new UserClient();
        User user = UserGenerator.create();
        orderClient = new OrderClient();
        accessToken = userClient.register(user, 200).as(AuthUser.class).getAccessToken();
    }

    @After
    public void deleteUser() {
        userClient.delete(accessToken, 202);
    }

    @Test
    @DisplayName("Получение пустого списка заказов конкретного пользователя с авторизацией")
    @Description("Получаем список -> проверяем тело")
    public void authUserNullOrderListTest() {
        Response response = orderClient.getUserOrdersList(accessToken, 200);
        orderClient.compareAuthUserOrderNullListResponseBody(response);
    }

    @Test
    @DisplayName("Получение не пустого списка заказов конкретного пользователя с авторизацией")
    @Description("Получаем список -> проверяем тело")
    public void authUserNotNullOrderListTest() {
        IngredientHashes ingredientHashes = orderClient.getTwoIngredientsHash(200);
        orderClient.create(
                accessToken,
                ingredientHashes,
                200
        );
        Response response = orderClient.getUserOrdersList(accessToken, 200);
        orderClient.compareAuthUserOrderNotNullListResponseBody(response, ingredientHashes);
    }
}
