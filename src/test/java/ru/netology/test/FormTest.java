package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class FormTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void tearDown() {
        cleanDatabase();
    }

    @Test
    public void shouldValidTest() {
        LoginPage loginPage = new LoginPage();
        var userInfo = DataHelper.validUser();
        loginPage.validLogin(userInfo);
        var verificationPage = loginPage.verifyOk();
        var code = SQLHelper.getVerificationCode();
        verificationPage.validNotification(code.getVerificationCode());
        var dashboardPage = verificationPage.verifyOk();
    }

    @Test
    public void shouldInvalidTestRandomLogin() {
        LoginPage loginPage = new LoginPage();
        var userInfo = DataHelper.randomLoginUser();
        loginPage.validLogin(userInfo);
        loginPage.errorNotificationVisible();
    }

    @Test
    public void shouldInvalidTestRandomPassword() {
        LoginPage loginPage = new LoginPage();
        var userInfo = DataHelper.randomPasswordUser();
        loginPage.validLogin(userInfo);
        loginPage.errorNotificationVisible();
    }

    @Test
    public void shouldInvalidTestUnregisteredUser() {
        LoginPage loginPage = new LoginPage();
        var userInfo = DataHelper.randomUser();
        loginPage.validLogin(userInfo);
        loginPage.errorNotificationVisible();
    }

    @Test //BUG
    public void shouldTestBlockedInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        var userInfo = DataHelper.randomPasswordUser();
        loginPage.validLogin(userInfo);
        loginPage.errorNotificationVisible();
        open("http://localhost:9999/");
        userInfo = DataHelper.randomPasswordUser();
        loginPage.validLogin(userInfo);
        loginPage.errorNotificationVisible();
        open("http://localhost:9999/");
        userInfo = DataHelper.randomPasswordUser();
        loginPage.validLogin(userInfo);
        loginPage.errorNotificationVisible();
        assertEquals(SQLHelper.getUserStatus(userInfo.getLogin()), "blocked");
        open("http://localhost:9999/");
        loginPage.validLogin(userInfo);
        loginPage.userBlocked();
    }
}
