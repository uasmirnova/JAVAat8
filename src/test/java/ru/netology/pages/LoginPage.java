package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private SelenideElement errorButton = $("[data-test-id=error-notification] button");

    public LoginPage() {
        loginField.should(visible);
        passwordField.should(visible);
        loginButton.should(visible);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public void errorNotificationVisible() {
        errorNotification.shouldBe(visible);
    }

    public void validLogin(DataHelper.UserInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void userBlocked() {
        errorNotification.should(visible);
        errorNotification.$("[class=notification__content]").should(text("Ошибка! " + "Пользователь заблокирован"));
        errorButton.click();
        errorNotification.should(hidden);
    }

    public VerificationPage verifyOk() {
        errorNotification.should(hidden);
        errorButton.should(hidden);
        return page(VerificationPage.class);
    }
}
