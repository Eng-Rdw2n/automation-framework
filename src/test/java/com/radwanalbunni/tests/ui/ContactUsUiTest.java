package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.data.models.domain.ContactUsDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.ContactUsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Contact Us")
@Feature("Contact Us Form")
public class ContactUsUiTest extends UiBaseTest {

    @Story("Submit a valid contact us form")
    @TestDataType("validContactSubmission")
    @TestDataFile("ui/contact")
    @Description("Verify user can submit the contact us form with valid data")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testContactUsForm(ContactUsDataModel data) {
        ContactUsPage contactUsPage = new ContactUsPage();
        Header header = new Header();
        header.navigateToContactUs();
        contactUsPage.fillContactForm(data);

        UiAssertions.assertTextEquals(
                contactUsPage.getSubmissionSuccessMessage(),
                data.getExpectedResult(),
                "Contact form confirmation message"
        );

    }

    @Story("Validate required contact email")
    @TestDataType("validContactSubmission")
    @TestDataFile("ui/contact")
    @Description("Verify the contact form does not submit when required fields are empty")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testContactFormRequiresEmail(ContactUsDataModel data) {
        Header header = new Header();
        ContactUsPage contactUsPage = new ContactUsPage();

        header.navigateToContactUs();
        contactUsPage.submitEmptyForm();

        UiAssertions.assertTextEquals(
                contactUsPage.getEmailValidationMessage(),
                data.getExpectedValidationMessage(),
                "Required contact email validation message"
        );
    }

    @Story("Reject invalid contact email")
    @TestDataType("invalidEmail")
    @TestDataFile("ui/contact")
    @Description("Verify the contact form rejects an invalid email format")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testContactFormRejectsInvalidEmail(ContactUsDataModel data) {
        Header header = new Header();
        ContactUsPage contactUsPage = new ContactUsPage();

        header.navigateToContactUs();
        contactUsPage.submitContactForm(data);

        UiAssertions.assertFalse(
                contactUsPage.isEmailValid(),
                "Invalid contact email should be rejected by browser validation"
        );
    }

    @Story("Submit contact form without attachment")
    @TestDataType("validWithoutAttachment")
    @TestDataFile("ui/contact")
    @Description("Verify a visitor can submit the contact form without an attachment")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testContactFormWithoutAttachment(ContactUsDataModel data) {
        Header header = new Header();
        ContactUsPage contactUsPage = new ContactUsPage();

        header.navigateToContactUs();
        contactUsPage.submitContactForm(data);
        contactUsPage.acceptAlert();

        UiAssertions.assertTextEquals(
                contactUsPage.getSubmissionSuccessMessage(),
                data.getExpectedResult(),
                "Contact form confirmation message"
        );
    }
}
