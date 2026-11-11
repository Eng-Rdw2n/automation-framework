package com.radwanalbunni.automation.ui.pages;

import com.radwanalbunni.automation.data.models.domain.PaymentCardDataModel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    // Checkout Page
    private static final By ORDER_COMMENT_TEXTAREA = By.cssSelector("textarea[name='message']");
    private static final By PLACE_ORDER_BUTTON = By.cssSelector("a[href='/payment']");

    // Payment
    private static final By NAME_ON_CARD_FIELD = By.cssSelector("[data-qa='name-on-card']");
    private static final By CARD_NUMBER_FIELD = By.cssSelector("[data-qa='card-number']");
    private static final By CVC_FIELD = By.cssSelector("[data-qa='cvc']");
    private static final By EXPIRY_MONTH_FIELD = By.cssSelector("[data-qa='expiry-month']");
    private static final By EXPIRY_YEAR_FIELD = By.cssSelector("[data-qa='expiry-year']");
    private static final By PAY_AND_CONFIRM_ORDER_BUTTON = By.cssSelector("[data-qa='pay-button']");
    // Order Confirmation Message
    private static final By ORDER_SUCCESS_MESSAGE = By.xpath("//p[contains(text(),'Congratulations! Your order has been confirmed!')]");

    private static final By DOWNLOAD_INVOICE_BUTTON = By.cssSelector("a[href*='/download_invoice/']");


    @Step("Add comment on checkout")
    public void addCommentOnCheckout(String comment) {
        type(ORDER_COMMENT_TEXTAREA, comment);
    }

    @Step("Click Place Order Button")
    public void clickPlaceOrderButton() {
        click(PLACE_ORDER_BUTTON);
    }

    @Step("Fill payment details")
    public void fillPaymentDetails(PaymentCardDataModel card) {
        type(NAME_ON_CARD_FIELD, card.getName());
        type(CARD_NUMBER_FIELD, card.getNumber());
        type(CVC_FIELD, card.getCvv());
        type(EXPIRY_MONTH_FIELD, card.getExpiryMonth());
        type(EXPIRY_YEAR_FIELD, card.getExpiryYear());
        click(PAY_AND_CONFIRM_ORDER_BUTTON);
    }

    public void submitEmptyPaymentDetails() {
        click(PAY_AND_CONFIRM_ORDER_BUTTON);
    }

    public String getPaymentNameValidationMessage() {
        return getNativeValidationMessage(NAME_ON_CARD_FIELD);
    }

    public String getPaymentCardNumberValidationMessage() {
        return getNativeValidationMessage(CARD_NUMBER_FIELD);
    }

    public String getPaymentCvvValidationMessage() {
        return getNativeValidationMessage(CVC_FIELD);
    }

    @Step("Download Invoice")
    public void clickDownloadInvoiceButton() {
        click(DOWNLOAD_INVOICE_BUTTON);
    }

    @Step("Get order confirmation message")
    public String getOrderSuccessMessage() {
        return getText(ORDER_SUCCESS_MESSAGE);
    }
}
