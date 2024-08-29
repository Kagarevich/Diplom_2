package site.nomoreparties.stellarburgers.api.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ReqSpec {

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    // TODO: проверить к концу разработки, нужна ли эта спецификация в итоге
    public static RequestSpecification getReqSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .build();
    }

    public static RequestSpecification getReqSpec(ContentType JSON) {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URI)
                .build();
    }

    public static RequestSpecification getReqSpec(String accessToken) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", accessToken)
                .setBaseUri(BASE_URI)
                .build();
    }

    public static RequestSpecification getReqSpec(String accessToken, ContentType JSON) {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .addHeader("Authorization", accessToken)
                .setBaseUri(BASE_URI)
                .build();
    }
}
