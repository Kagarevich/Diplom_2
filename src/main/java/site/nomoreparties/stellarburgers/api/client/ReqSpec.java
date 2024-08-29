package site.nomoreparties.stellarburgers.api.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class ReqSpec {

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getNotAuthReqSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URI)
                .build();
    }

    public static RequestSpecification getAuthReqSpec(String accessToken) {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .addHeader("Authorization", accessToken)
                .setBaseUri(BASE_URI)
                .build();
    }
}
