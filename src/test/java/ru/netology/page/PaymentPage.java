package ru.netology.page;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import java.time.Duration;

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
    //Если не заполнены все поля, появляется сообщение под каждым полем "неверный формат". Как описать все такие моменты?
// Я заметил, что во всех не заполненных полях формы, есть селектор input__sub, тогда как сообщения под полями не всегда "Неверный формат".
// Можно ли сделать одну переменную и привязать ее к селектору input__sub, или лучше сделать две переменные?
// На общую пустую отправку формы и отдельно на "неверный формат"?
    private SelenideElement invalidData = $(".input__sub"); // Вот такая переменная, например, подойдет для методов с любым невалидным полем.
    // Или все таки нужно создавать переменные для невалидных данных на каждое поле?
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement invalidDurationCard = $(byText("Неверно указан срок действия карты"));
    private SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения"));
    private SelenideElement cardExpiredError = $(byText("Истёк срок действия карты"));


    public PaymentPage payTourByCard() {
        payButton.click();
        return new PaymentPage();
    }

    public PaymentPage() {
        header.shouldBe(visible).shouldHave(exactText("Оплата по карте")); //Не будет ли ошибкой
        // не указывать вторую часть метода? Так как переменная header и так содержит указанный текст?
    }

    private void clearField() {
        cardNumberField.sendKeys(CONTROL + "A", DELETE);
        monthField.sendKeys(CONTROL + "A", DELETE);
        yearField.sendKeys(CONTROL + "A", DELETE);
        cardHolderField.sendKeys(CONTROL + "A", DELETE);
        cvvNumberField.sendKeys(CONTROL + "A", DELETE);
    }


    public void sendPaymentFormByCard(String card, String month, String year, String name, String cvv) {
        clearField();
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        cardHolderField.setValue(name);
        cvvNumberField.setValue(cvv);
        nextButton.click();
    }

    public void successSendingForm(String card, String month, String year, String name, String cvv) {
        sendPaymentFormByCard(card, month, year, name, cvv);
        successNotification.shouldBe(visible, Duration.ofSeconds(13)).shouldHave(exactText("Успешно"));
    }

    public void unSuccessSendingForm(String card, String month, String year, String name, String cvv) {
        sendPaymentFormByCard(card, month, year, name, cvv);
        errorNotification.shouldBe(visible, Duration.ofSeconds(13)).shouldHave(exactText("Ошибка"));
    }

    public void sendClearForm() {
        clearField();
        nextButton.click();
        invalidData.shouldBe(visible); //wrongFormatError.shouldBe(visible);
    }

    public void formatError() {
        wrongFormatError.shouldBe(visible);
    }

    public void invalidDurationCardError() {
        invalidDurationCard.shouldBe(visible);
    }

    public void requiredFieldToFillError() {
        requiredFieldError.shouldBe(visible);
    }

    public void cardExpiredError() {
        cardExpiredError.shouldBe(visible);
    }

}
