package site.nomoreparties.stellarburgers.nonparameterized;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.OrderClient;
import site.nomoreparties.stellarburgers.constant.Message;
import site.nomoreparties.stellarburgers.model.InfoResponse;

@DisplayName("Тест \"Получение заказов конкретного пользователя\" \"неавторизованный пользователь\"")
public class NotAuthUserOrderListTest {

    private OrderClient orderClient;

    @Before
    public void init() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя без авторизации")
    @Description("Пытаемся получить список -> проверяем тело ответа ошибки")
    public void notAuthUserOrderListTest() {
        Response response = orderClient.getUserOrdersList(401);
        orderClient.compareResponseBody(
                new InfoResponse(
                        false,
                        Message.SHOULD_BE_AUTHORISED_ERROR
                ),
                response,
                InfoResponse.class
        );
    }
}
