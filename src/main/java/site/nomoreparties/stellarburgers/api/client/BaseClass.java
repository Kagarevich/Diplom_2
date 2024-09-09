package site.nomoreparties.stellarburgers.api.client;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;

public class BaseClass {

    @Step("Сравнение тела ответа с ожидаемым")
    @Description("Я переопределил методы toString() во всех модельках. Это было быстрее, чем переопределять equals()")
    public void compareResponseBody(Object expected, Response actual, Class<?> expectedClass) {
        Assert.assertEquals(
                "Тело ответа не совпадает",
                expected.toString(),
                actual.body().as(expectedClass).toString());
    }

    @Step("проверка тела ответа через маппинг данных")
    @Description("Смотрим, корректный ли ответ по содержанию для случаев, " +
            "когда мы не можем знать заранее тело ответа")
    public void matchResponseBody(Response response, Class<?> expectedClass) {
        boolean result;
        try {
            response.body().as(expectedClass);
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println(e.getMessage());
        }
        Assert.assertTrue("Ошибка мэтчинга данных", result);
    }
}
