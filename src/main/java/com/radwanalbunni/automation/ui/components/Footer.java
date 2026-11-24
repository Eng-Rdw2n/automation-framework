package com.radwanalbunni.automation.ui.components;

import com.radwanalbunni.automation.ui.pages.BasePage;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Footer extends BasePage {


    // Email input
    private static final By EMAIL_INPUT = By.id("susbscribe_email");

    // Subscribe button
    private static final By SUBSCRIBE_BUTTON = By.id("subscribe");

    // Confirmation shown after the newsletter subscription is submitted.
    private static final By SUBSCRIPTION_SUCCESS_MESSAGE =
            By.cssSelector("div.alert-success.alert");

    public void subscribe(UserDataModel data) {
        subscribe(data.getEmail());
    }

    public void subscribe(String email) {
        Allure.step("Subscribe to newsletter", () -> {
            type(EMAIL_INPUT, email);
            click(SUBSCRIBE_BUTTON);
        });
    }

    @Step("Get subscription result message")
    public String getSubscriptionSuccessMessage() {
        return getText(SUBSCRIPTION_SUCCESS_MESSAGE);
    }

    public void submitEmptySubscription() {
        click(SUBSCRIBE_BUTTON);
    }

    public String getEmailValidationMessage() {
        return getNativeValidationMessage(EMAIL_INPUT);
    }

    public boolean isSubscriptionEmailValid() {
        return isFieldValid(EMAIL_INPUT);
    }

}
