package site.nomoreparties.stellarburgers;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.client.UserClient;
import site.nomoreparties.stellarburgers.model.AuthUser;
import site.nomoreparties.stellarburgers.model.User;
import site.nomoreparties.stellarburgers.model.generator.UserGenerator;

public class UserTest {

    UserClient userClient;
    User user;
    String token;

    @Test
    public void simpleTest() {
        userClient = new UserClient();
        user = UserGenerator.create();
        Response resp = userClient.register(user, 200);
        token = resp.body().as(AuthUser.class).getAccessToken();
        userClient.matchResponseBody(resp, AuthUser.class);
        userClient.compareResponseBody(new AuthUser(
                true,
                new User(user.getEmail(), user.getName()),
                resp.body().as(AuthUser.class).getAccessToken(),
                resp.body().as(AuthUser.class).getRefreshToken()),
                resp,
                AuthUser.class);
    }

    @After
    public void after() {
        userClient.delete(token, 202);
    }
}
