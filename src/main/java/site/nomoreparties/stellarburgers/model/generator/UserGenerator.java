package site.nomoreparties.stellarburgers.model.generator;

import org.apache.commons.lang3.RandomStringUtils;
import site.nomoreparties.stellarburgers.model.User;

public class UserGenerator {

    /**
     * create random user
     * @return random user
     */
    public static User create() {
        String shortString = RandomStringUtils.random(3, true, false).toLowerCase();
        String longString = RandomStringUtils.random(10, true, false).toLowerCase();
        return new User(
                String.format(
                        "%s@%s.%s",
                        longString,
                        shortString,
                        shortString),
                longString,
                longString
        );
    }
}
