package com.radwanalbunni.automation.ui.pages;

import com.radwanalbunni.automation.data.models.domain.ContactUsDataModel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ContactUsPage extends BasePage {

    // Fields
    private static final By NAME_FIELD = By.cssSelector("[data-qa='name']");
    private static final By EMAIL_FIELD = By.cssSelector("[data-qa='email']");
    private static final By SUBJECT_FIELD = By.cssSelector("[data-qa='subject']");
    private static final By MESSAGE_FIELD = By.cssSelector("[data-qa='message']");
    private static final By UPLOAD_FIELD = By.cssSelector("input[name='upload_file']");
    private static final By SUBMIT_BUTTON = By.cssSelector("[data-qa='submit-button']");
    private static final By SUCCESS_MESSAGE = By.cssSelector("div.status.alert.alert-success");

    @Step("Fill out and submit the contact form")
    public void fillContactForm(ContactUsDataModel data) {
        enterContactDetails(data);
        uploadFile(UPLOAD_FIELD, data.getFilePath());
        click(SUBMIT_BUTTON);
        acceptAlert();
    }

    /**
     * Enters contact details and submits without uploading a file or accepting an alert.
     */
    public void submitContactForm(ContactUsDataModel data) {
        enterContactDetails(data);
        click(SUBMIT_BUTTON);
    }

    private void enterContactDetails(ContactUsDataModel data) {
        type(NAME_FIELD, data.getUser().getName());
        type(EMAIL_FIELD, data.getUser().getEmail());
        type(SUBJECT_FIELD, data.getSubject());
        type(MESSAGE_FIELD, data.getMessage());
    }

    public String getSubmissionSuccessMessage() {
        return getText(SUCCESS_MESSAGE);
    }

    public void submitEmptyForm() {
        click(SUBMIT_BUTTON);
    }

    public String getEmailValidationMessage() {
        return getNativeValidationMessage(EMAIL_FIELD);
    }

    public boolean isEmailValid() {
        return isFieldValid(EMAIL_FIELD);
    }

}
