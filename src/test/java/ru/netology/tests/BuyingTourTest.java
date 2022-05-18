package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.InitPage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyingTourTest {
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
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы по действующей карте")
    void shouldSuccessPayTourWithApprovedCard() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Заполнение и отправка формы по НЕ действующей карте")
    void shouldSuccessPayTourWithDeclinedCard() {
        var paymentPage = initPage.payForTour();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.unSuccessSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(),
                declinedPayment.getCardHolder(), declinedPayment.getCvv()); //БАГ - Можно купить тур с заблокированной картой
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы по действующей карте")
    void shouldSuccessPayTourInCreditWithApprovedCard() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Заполнение и отправка формы по НЕ действующей карте")
    void shouldSuccessPayTourInCreditWithDeclinedCard() {
        var creditPage = initPage.buyWithCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.unSuccessSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(), declinedPayment.getYear(),
                declinedPayment.getCardHolder(), declinedPayment.getCvv()); //БАГ - Можно купить тур с заблокированной картой
    }

    @Epic("Смена страниц оплаты")
    @Feature("Позитивные сценарии")
    @Story("Без карты")
    @Test
    @Description("Переключение со страницы оплаты на страницу оплаты в кредит и обратно")
    void shouldSwitchFromPaymentToCreditAndBackPayment() {
        var paymentPage = initPage.payForTour();
        var creditPage = initPage.buyWithCredit();
        var newPaymentPage = initPage.payForTour();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы по активной карте со сроком истечения 59 месяцев")
    void shouldSuccessPayTourWithApprovedCardValidityPeriod59Month() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(59);
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы в кредит по активной карте  со сроком истечения 59 месяцев")
    void shouldSuccessPayTourWithApprovedInCreditValidityPeriod59Month() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(59);
        creditPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы по активной карте со сроком истечения 60 месяцев")
    void shouldSuccessPayTourWithApprovedCardValidityPeriod60Month() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(60);
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы в кредит по активной карте  со сроком истечения 60 месяцев")
    void shouldSuccessPayTourWithApprovedCreditValidityPeriod60Month() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(60);
        creditPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы по активной карте со сроком истечения более 5 лет")
    void shouldGetErrorPayTourWithApprovedCardValidityPeriodMore5Years() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(61);
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.cardExpiredError(); //БАГ Не выдает ошибку по карте со сроком более 5 лет
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы в кредит по активной карте со сроком истечения более 5 лет")
    void shouldGetErrorPayTourInCreditWithApprovedValidityPeriodMore5Years() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(61);
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.cardExpiredError(); //БАГ Не выдает ошибку по карте со сроком более 5 лет
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы по активной карте со сроком истечения более 6 лет")
    void shouldGetErrorPayTourWithApprovedCardValidityPeriodMore6Years() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(71);
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.cardExpiredError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы в кредит по активной карте со сроком истечения более 6 лет")
    void shouldGetErrorPayTourInCreditWithApprovedValidityPeriodMore6Years() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(81);
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.cardExpiredError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы по активной карте со сроком истечения 1 месяц")
    void shouldSuccessPayTourWithApprovedCardValidityPeriod1Month() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(1);
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы в кредит по активной карте со сроком истечения 1 месяц")
    void shouldSuccessPayTourInCreditWithApprovedCardValidityPeriod1Month() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(1);
        creditPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы по активной карте со сроком истечения в текущем месяце")
    void shouldSuccessPayTourWithApprovedCardValidityPeriodСurrentMonth() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(0);
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Заполнение и отправка формы в кредит по активной карте со сроком истечения в текущем месяце")
    void shouldSuccessPayTourInCreditWithApprovedCardValidityPeriodСurrentMonth() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(0);
        creditPage.successSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы по активной карте c истекшим сроком")
    void shouldGetErrorByPayTourWithApprovedCardByЕxpiredPeriod() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(-1);
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.cardExpiredError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы в кредит по активной карте c истекшим сроком")
    void shouldGetErrorByPayTourInCreditWithApprovedCardByЕxpiredPeriod() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(-1);
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.cardExpiredError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы по активной карте c месяцем 00")
    void shouldGetErrorByPayTourWithApprovedCardWithZeroMonth() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.cardExpiredError();// БАГ Успешная отправка формы при параметре в поле "Месяц" - 00
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы в кредит по активной карте c месяцем 00")
    void shouldGetErrorByPayTourInCreditWithApprovedCardWithZeroMonth() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.cardExpiredError();// БАГ Успешная отправка формы при параметре в поле "Месяц" - 00
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен успешно заполнить и отправить форму по активной карте с месяцем 01")
    void shouldSuccessPayTourWithApprovedCardIn01Month() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен успешно заполнить и отправить форму в кредит по активной карте с месяцем 01")
    void shouldSuccessPayTourInCreditWithApprovedCardIn01Month() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.successSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен успешно заполнить и отправить форму по активной карте с месяцем 12")
    void shouldSuccessPayTourWithApprovedCardIn12Month() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.successSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Позитивные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен успешно заполнить и отправить форму в кредит по активной карте с месяцем 12")
    void shouldSuccessPayTourInCreditWithApprovedCardIn12Month() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.successSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы по активной карте c месяцем 13")
    void shouldGetErrorByPayTourWithApprovedCardWith13Month() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.cardExpiredError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы в кредит по активной карте c месяцем 13")
    void shouldGetErrorByPayTourInCreditWithApprovedCardWith13Month() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.cardExpiredError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен показывать ошибку при отправке пустой формы страницы оплаты")
    void shouldGetErrorByPayTourFromPaymentPageBySendClearForm() {
        var paymentPage = initPage.payForTour();
        paymentPage.sendClearForm();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен показывать ошибку при отправке пустой формы со станицы кредита")
    void shouldGetErrorByPayTourFromCreditPageWithSendClearForm() {
        var creditPage = initPage.buyWithCredit();
        creditPage.sendClearForm();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем карты")
    void shouldGetErrorByPayTourWithClearFieldCard() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(" ", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.formatError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем карты")
    void shouldGetErrorByPayTourInCreditWithClearFieldCard() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(" ", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.formatError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем месяц")
    void shouldGetErrorByPayTourWithClearFieldMonth() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), " ",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.formatError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем месяц")
    void shouldGetErrorByPayTourInCreditWithClearFieldMonth() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), " ",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.formatError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем год")
    void shouldGetErrorByPayTourWithClearFieldYear() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                " ", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.formatError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем месяц")
    void shouldGetErrorByPayTourInCreditWithClearFieldYear() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                " ", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.formatError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем владелец")
    void shouldGetErrorByPayTourWithClearFieldHolder() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), " ", approvedPayment.getCvv());
        paymentPage.requiredFieldToFillError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем владелец")
    void shouldGetErrorByPayTourInCreditWithClearFieldHolder() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), " ", approvedPayment.getCvv());
        creditPage.requiredFieldToFillError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем CVV")
    void shouldGetErrorByPayTourWithClearFieldCVV() {
        var paymentPage = initPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), " ");
        paymentPage.formatError();
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен показывать ошибку при отправке формы с пустым полем CVV")
    void shouldGetErrorByPayTourInCreditWithClearFieldCVV() {
        var creditPage = initPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.sendPaymentFormByCard(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), " ");
        creditPage.formatError();
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод латинских символов в поле карта")
    void shouldGetErrorBySendFieldCardLatinSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод латинских символов в поле карта")
    void shouldGetErrorBySendFieldCardLatinSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCardNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод кириллицы в поле карта")
    void shouldGetErrorBySendFieldCardCyrillSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод кириллицы в поле карта")
    void shouldGetErrorBySendFieldCardCyrillSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCardNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getCardNumberField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод символов в поле карта")
    void shouldGetErrorBySendFieldCardSpecSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCardNumberField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод символов в поле карта")
    void shouldGetErrorBySendFieldCardSpecSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCardNumberField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", creditPage.getCardNumberField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод латинских символов в поле месяц")
    void shouldGetErrorBySendFieldMonthLatinSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getMonthField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод латинских символов в поле месяц")
    void shouldGetErrorBySendFieldMonthLatinSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getMonthField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод кириллицы в поле месяц")
    void shouldGetErrorBySendFieldMonthCyrillSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getMonthField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод кириллицы в поле месяц")
    void shouldGetErrorBySendFieldMonthCyrillSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getMonthField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getMonthField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод символов в поле месяц")
    void shouldGetErrorBySendFieldMonthSpecSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getMonthField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод символов в поле месяц")
    void shouldGetErrorBySendFieldMonthSpecSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getMonthField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", creditPage.getMonthField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод латинских символов в поле год")
    void shouldGetErrorBySendFieldYearLatinSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getYearField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод латинских символов в поле год")
    void shouldGetErrorBySendFieldYearLatinSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getYearField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод кириллицы в поле год")
    void shouldGetErrorBySendFieldYearCyrillSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getYearField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод кириллицы в поле год")
    void shouldGetErrorBySendFieldYearCyrillSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getYearField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getYearField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод символов в поле год")
    void shouldGetErrorBySendFieldYearSpecSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getYearField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод символов в поле год")
    void shouldGetErrorBySendFieldYearSpecSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getYearField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", creditPage.getYearField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод цифр в поле владелец")
    void shouldGetErrorBySendFieldCardHolderNumbers() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCardHolderField().setValue("0121345678");
        assertEquals("", paymentPage.getCardHolderField().getValue()); // БАГ - Поле сохраняет цифры
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод цифр в поле владелец")
    void shouldGetErrorBySendFieldCardHolderNumbersInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCardHolderField().setValue("0121345678");
        assertEquals("", creditPage.getCardHolderField().getValue()); // БАГ - Поле сохраняет цифры
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод спец.символов в поле владелец")
    void shouldGetErrorBySendFieldCardHolderSpecSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCardHolderField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", paymentPage.getCardHolderField().getValue()); // БАГ - Поле сохраняет спец. Символы
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод спец.символов в поле владелец")
    void shouldGetErrorBySendFieldCardHolderSpecSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCardHolderField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", creditPage.getCardHolderField().getValue()); // БАГ - Поле сохраняет спец. Символы
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод латинских символов в поле CVV")
    void shouldGetErrorBySendFieldCvvNumberLatinSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCvvNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод латинских символов в поле CVV")
    void shouldGetErrorBySendFieldCvvNumberLatinSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCvvNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }

    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод кириллицы в поле CVV")
    void shouldGetErrorBySendFieldCvvNumberCyrillSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCvvNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод кириллицы в поле CVV")
    void shouldGetErrorBySendFieldCvvNumberCyrillSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCvvNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }
    @Epic("Покупка тура по дебетовой карте")
    @Feature("Негативные сценарии")
    @Story("Без карты")
    @Test
    @Description("Должен запретить ввод символов в поле CVV")
    void shouldGetErrorBySendFieldCvvNumberSpecSymbols() {
        var paymentPage = initPage.payForTour();
        paymentPage.getCvvNumberField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Epic("Покупка тура в кредит")
    @Feature("Негативные сценарии")
    @Story("Не активная карта")
    @Test
    @Description("Должен запретить ввод символов в поле CVV")
    void shouldGetErrorBySendFieldCvvNumberSpecSymbolsInCreditPage() {
        var creditPage = initPage.buyWithCredit();
        creditPage.getCvvNumberField().setValue("+@&?-##^^^@@!!!!");
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }





}
