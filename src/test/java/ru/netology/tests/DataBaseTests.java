package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DataHelperSQL;
import ru.netology.page.InitPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseTests {
    InitPage initPage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
        initPage = new InitPage();
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Не сохраняет номер карты в БД")
    @Story("Активная карта")
    @Test
    @Description("Должен выдавать пустое поле номера карты в БД")
    void shouldNotSaveNumberApprovedCardByPayTour() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.popUpNotification();
        assertEquals("null", DataHelperSQL.getCreditId());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Не сохраняет номер карты в БД")
    @Story("Активная карта")
    @Test
    @Description("Должен выдавать пустое поле номера карты в БД")
    void shouldNotSaveNumberApprovedCardByPayTourInCredit() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.popUpNotification();
        assertEquals("null", DataHelperSQL.getCreditId());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Сохранение статуса платежа в БД")
    @Story("Активная карта")
    @Test
    @Description("Должен сохранять платеж с действующей карты в БД как одобренный")
    void shouldSaveStatusPaymentInDataBaseByPayTour() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.popUpNotification();
        assertEquals("APPROVED", DataHelperSQL.getPaymentStatus());
    }
    @Epic("Покупка тура в кредит")
    @Feature("Сохранение статуса платежа в БД")
    @Story("Активная карта")
    @Test
    @Description("Должен сохранять платеж в кредит с действующей карты в БД как одобренный")
    void shouldSaveStatusPaymentInDataBaseByPayTourInCredit() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.popUpNotification();
        assertEquals("APPROVED", DataHelperSQL.getCreditStatus());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Сохранение статуса платежа в БД")
    @Story("Не активная карта")
    @Test
    @Description("Должен сохранять платеж с не действительной карты в БД как отклоненный")
    void shouldSaveStatusPaymentInDataBaseByPayTourWithDeclinedCard() {
        var paymentPage = initPage.payForTour();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(),
                declinedPayment.getCardHolder(), declinedPayment.getCvv());
        paymentPage.popUpNotification();
        assertEquals("DECLINED", DataHelperSQL.getPaymentStatus());
    }
    @Epic("Покупка тура в кредит")
    @Feature("Сохранение статуса платежа в БД")
    @Story("Не активная карта")
    @Test
    @Description("Должен сохранять платеж в кредит с не действующей карты в БД как отклоненный")
    void shouldSaveStatusPaymentInDataBaseByPayTourInCreditWithDeclinedCard() {
        var creditPage = initPage.buyWithCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(),
                declinedPayment.getCardHolder(), declinedPayment.getCvv());
        creditPage.popUpNotification();
        assertEquals("DECLINED", DataHelperSQL.getCreditStatus());
    }





}
