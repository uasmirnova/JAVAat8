package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private SelenideElement errorButton = $("[data-test-id=error-notification] button");

    public VerificationPage() {
        codeField.should(visible);
        verifyButton.should(visible);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public void errorNotificationVisible() {
        errorNotification.shouldBe(visible);
    }

    public void validNotification(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public DashboardPage verifyOk() {
        errorNotification.should(hidden);
        errorButton.should(hidden);
        return page(DashboardPage.class);
    }
}
