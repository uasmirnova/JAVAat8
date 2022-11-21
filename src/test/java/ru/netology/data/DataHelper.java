package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class UserInfo {
        private String login;
        private String password;
    }

    public static UserInfo validUser() {
        return new UserInfo("vasya", "qwerty123");
    }

    public static UserInfo randomLoginUser() {
        return new UserInfo(randomLogin(), "qwerty123");
    }

    public static UserInfo randomPasswordUser() {
        return new UserInfo("vasya", randomPassword());
    }

    public static UserInfo randomUser() {
        return new UserInfo(randomLogin(), randomPassword());
    }

    public static String randomLogin() {
        return faker.name().username();
    }

    public static String randomPassword() {
        return faker.internet().password();
    }

    @Value
    public static class VerificationCode {
        private String verificationCode;
    }

    public static VerificationCode randomCode() {
        return new VerificationCode(String.valueOf(faker.number().numberBetween(100_000, 999_999)));
    }
}
