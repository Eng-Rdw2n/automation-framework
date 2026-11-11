package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.data.models.domain.SubscriptionDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.ui.components.Footer;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Subscription")
@Feature("Newsletter Subscription")
public class SubscriptionUiTest extends UiBaseTest {


    @Story("Subscribe through the footer")
    @TestDataType("validSubscription")
    @TestDataFile("ui/subscription")
    @Description("Verify a visitor can subscribe to the newsletter from the footer")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testNewsletterSubscription(SubscriptionDataModel data) {
        Footer footer = new Footer();
        footer.subscribe(data.getEmail());

        UiAssertions.assertTextEquals(
                footer.getSubscriptionSuccessMessage(),
                data.getExpectedResult(),
                "Subscription confirmation message"
        );


    }

    @Story("Validate required subscription email")
    @TestDataType("validSubscription")
    @TestDataFile("ui/subscription")
    @Description("Verify the subscription form does not submit when the email is empty")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSubscriptionRequiresEmail(SubscriptionDataModel data) {
        Footer footer = new Footer();

        footer.submitEmptySubscription();

        UiAssertions.assertTextEquals(
                footer.getEmailValidationMessage(),
                data.getExpectedValidationMessage(),
                "Required subscription email validation message"
        );
    }

    @Story("Reject invalid subscription email")
    @TestDataType("invalidEmail")
    @TestDataFile("ui/subscription")
    @Description("Verify the subscription form rejects an invalid email format")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSubscriptionRejectsInvalidEmail(SubscriptionDataModel data) {
        Footer footer = new Footer();

        footer.subscribe(data.getEmail());

        UiAssertions.assertFalse(
                footer.isSubscriptionEmailValid(),
                "Invalid subscription email should be rejected by browser validation"
        );
    }
}
