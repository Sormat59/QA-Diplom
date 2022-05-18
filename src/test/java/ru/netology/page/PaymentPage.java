package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Data;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

@Data
public class PaymentPage {
    private SelenideElement payButton = $(byText("Купить"));
    private SelenideElement header = $(byText("Оплата по карте"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement cardHolderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvvNumberField = $("[placeholder='999']");
    private SelenideElement nextButton = $(byText("Продолжить"));
    private SelenideElement wrongFormatError = $(byText("Неверный формат"));
    //Если не заполнены все поля, появляется сообщение под каждым полем "неверный формат", кроме одного. Как в таком случае вывести переменную под отправку пустой формы?
// Я заметил, что во всех не заполненных полях формы, есть селектор input__sub, тогда как сообщения под полями не всегда "Неверный формат".
// Можно ли сделать одну переменную и привязать ее к селектору input__sub, или лучше сделать две переменные?
    private SelenideElement invalidData = $(".input__sub"); // Вот такая переменная, например, подойдет для методов с любым невалидным полем.
    //    Но не будет ли ошибкой создавать метод с отправкой пустой формы по этому селектору?
    // Или все таки нужно создавать переменные для невалидных данных на каждое поле?
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения")).parent().$(".input__sub");
    private SelenideElement cardExpiredError = $(byText("Неверно указан срок действия карты")).parent().$(".input__sub");
    private SelenideElement popUpNotification = $(".notification");


    public PaymentPage payTourByCard() {
        payButton.click();
        return new PaymentPage();
    }

    public PaymentPage() {
        header.shouldBe(visible).shouldHave(exactText("Оплата по карте"));
    }

    private void clearField() {
        cardNumberField.sendKeys(CONTROL + "A", DELETE);
        monthField.sendKeys(CONTROL + "A", DELETE);
        yearField.sendKeys(CONTROL + "A", DELETE);
        cardHolderField.sendKeys(CONTROL + "A", DELETE);
        cvvNumberField.sendKeys(CONTROL + "A", DELETE);
    }

    @Step("Заполнение формы {")
    public void sendPaymentFormByCard(String card, String month, String year, String name, String cvv) {
        clearField();
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        cardHolderField.setValue(name);
        cvvNumberField.setValue(cvv);
        nextButton.click();
    }

    @Step("Заполняем форму валидными данными {card, month, year, name, cvv}")
    public void successSendingForm(String card, String month, String year, String name, String cvv) {
        sendPaymentFormByCard(card, month, year, name, cvv);
        successNotification.shouldBe(visible, Duration.ofSeconds(13)).shouldHave(exactText("Успешно\n" + "Операция одобрена Банком."));
    }

    @Step("Заполняем форму  не валидными данными {card, month, year, name, cvv}")
    public void unSuccessSendingForm(String card, String month, String year, String name, String cvv) {
        sendPaymentFormByCard(card, month, year, name, cvv);
        errorNotification.shouldBe(visible, Duration.ofSeconds(13)).shouldHave(exactText("Ошибка\n" + "Ошибка! Банк отказал в проведении операции."));
    }

    public void sendClearForm() {
        clearField();
        nextButton.click();
        invalidData.shouldBe(visible); //wrongFormatError.shouldBe(visible);
    }

    public void formatError() {
        wrongFormatError.shouldBe(visible);
    }


    public void requiredFieldToFillError() {
        requiredFieldError.shouldBe(visible);
    }

    public void cardExpiredError() {
        cardExpiredError.shouldBe(visible);
    }

    public void popUpNotification() {
        popUpNotification.shouldBe(visible, Duration.ofSeconds(13));
    }

}
