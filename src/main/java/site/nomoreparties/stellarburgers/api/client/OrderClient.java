package site.nomoreparties.stellarburgers.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;
import site.nomoreparties.stellarburgers.constant.PathAPI;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.IngredientHashes;
import site.nomoreparties.stellarburgers.model.OrderCreateResponse;
import site.nomoreparties.stellarburgers.model.UserOrdersList;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient extends BaseClass {

    @Step("Авторизованный пользователь. Создание заказа")
    public Response create(String accessToken, IngredientHashes ingredientHashes, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(accessToken, JSON))
                .body(ingredientHashes)
                .post(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Неавторизованный пользователь. Создание заказа")
    public Response create(IngredientHashes ingredientHashes, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(JSON))
                .body(ingredientHashes)
                .post(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Авторизованный пользователь. Получение заказов конкретного пользователя")
    public Response getUserOrdersList(String accessToken, int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec(accessToken))
                .get(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Неавторизованный пользователь. Получение заказов конкретного пользователя")
    public Response getUserOrdersList(int expectedStatusCode) {
        return given()
                .spec(ReqSpec.getReqSpec())
                .get(PathAPI.ORDERS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    @Step("Получение списка из двух ингредиентов")
    public IngredientHashes getTwoIngredientsHash(int expectedStatusCode) {
        Response response = given()
                .spec(ReqSpec.getReqSpec())
                .get(PathAPI.INGREDIENTS)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract()
                .response();
        return new IngredientHashes(
                new String[] {
                        response.body().path("data[0]._id"),
                        response.body().path("data[1]._id")
                });
    }

    @Step("Мэтчинг данных создания заказа и сравнение поля success")
    public void compareAuthOrderCreateResponseBody(Response response) {
        matchResponseBody(response, OrderCreateResponse.class);
        Assert.assertTrue("success != true", response.as(OrderCreateResponse.class).getSuccess());
    }

    @Step("Мэтчинг данных пустого списка заказов пользователя и сравнение поля success")
    public void compareAuthUserOrderNullListResponseBody(Response response) {
        matchResponseBody(response, UserOrdersList.class);
        Assert.assertTrue("success != true", response.as(UserOrdersList.class).getSuccess());
    }

    @Step("Мэтчинг данных не пустого списка заказов пользователя, сравнение поля success и ингредиентов")
    public void compareAuthUserOrderNotNullListResponseBody(
            Response response,
            IngredientHashes ingredientHashes) {
        matchResponseBody(response, UserOrdersList.class);
        UserOrdersList userOrdersList = response.as(UserOrdersList.class);
        Assert.assertTrue("success != true", userOrdersList.getSuccess());
        String orderListIngredientsAsString = String.join(
                ",",
                userOrdersList
                .getOrders()[0]
                .getIngredients()
        );
        String ingredientHashesAsString = String.join(
                ",",
                ingredientHashes.getIngredients()
        );
        Assert.assertEquals(
                "Список ингредиентов не совпадает с ожидаемым",
                ingredientHashesAsString,
                orderListIngredientsAsString);
    }
}
