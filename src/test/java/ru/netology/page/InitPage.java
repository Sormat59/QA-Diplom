package ru.netology.page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

public class InitPage {
    private SelenideElement header = $(byText("Путешествие дня"));
    private SelenideElement payButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public InitPage() {
        header.shouldBe(visible);
    }

    public PaymentPage payForTour() {
        payButton.click();
        return new PaymentPage();
    }

    public CreditPage buyWithCredit() {
        creditButton.click();
        return new CreditPage();
    }

}
