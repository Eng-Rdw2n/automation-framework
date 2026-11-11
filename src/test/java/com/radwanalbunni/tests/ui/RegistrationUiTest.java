package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.core.driver.DriverFactory;
import com.radwanalbunni.automation.data.models.domain.RegistrationDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.AccountPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Epic("Registration")
@Feature("User Registration")
public class RegistrationUiTest extends UiBaseTest {

    @Story("Valid user registration")
    @Severity(SeverityLevel.CRITICAL)
    @TestDataType("validRegistration")
    @TestDataFile("ui/registration")
    @Description("Verify user can register with valid information")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testUserRegistration(RegistrationDataModel data) {
        Header header = new Header();
        header.navigateToLoginSignup();
        AccountPage accountPage = new AccountPage();
        accountPage.signup(data);
        accountPage.addAccountInfo(data);
        accountPage.fillAddressDetails(data);

        UiAssertions.assertTextEquals(
                accountPage.getAccountCreatedMessage(),
                data.getExpectedResult(),
                "Account creation confirmation message"
        );

    }

    @Story("Delete newly registered account")
    @Severity(SeverityLevel.NORMAL)
    @TestDataType("validRegistration")
    @TestDataFile("ui/registration")
    @Description("Verify a newly registered user can delete their account")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testDeleteNewlyRegisteredAccount(RegistrationDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();

        header.navigateToLoginSignup();
        accountPage.signup(data);
        accountPage.addAccountInfo(data);
        accountPage.fillAddressDetails(data);
        header.navigateToHome();
        accountPage.deleteAccount();

        UiAssertions.assertTextEquals(
                accountPage.getAccountDeletedMessage(),
                data.getExpectedDeletionResult(),
                "Account deletion confirmation message"
        );
    }

    @Story("Registration with an existing email")
    @TestDataType("duplicateEmail")
    @TestDataFile("ui/registration")
    @Description("Verify registration is rejected when the email address already exists")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testRegistrationWithExistingEmail(RegistrationDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();

        header.navigateToLoginSignup();
        accountPage.signup(data);

        UiAssertions.assertTextEquals(
                accountPage.getSignupEmailExistsMessage(),
                data.getExpectedResult(),
                "Existing email registration message"
        );
    }

    @Story("Validate required signup name")
    @TestDataType("missingSignupName")
    @TestDataFile("ui/registration")
    @Description("Verify signup does not submit when the name is empty")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSignupRequiresName(RegistrationDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();

        header.navigateToLoginSignup();
        accountPage.signup(data);

        UiAssertions.assertTextEquals(
                accountPage.getSignupNameValidationMessage(),
                data.getExpectedResult(),
                "Required signup name validation message"
        );
    }

    @Story("Validate required signup email")
    @TestDataType("missingSignupEmail")
    @TestDataFile("ui/registration")
    @Description("Verify signup does not submit when the email is empty")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSignupRequiresEmail(RegistrationDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();

        header.navigateToLoginSignup();
        accountPage.signup(data);

        UiAssertions.assertTextEquals(
                accountPage.getSignupEmailValidationMessage(),
                data.getExpectedResult(),
                "Required signup email validation message"
        );
    }

    /**
     * A successful registration ends on the Account Created page. From there, return home
     * and delete the generated account so the public sandbox is not filled with test data.
     */
    @AfterMethod(alwaysRun = true)
    public void deleteGeneratedAccount() {
        if (!DriverFactory.isDriverActive()) {
            return;
        }

        AccountPage accountPage = new AccountPage();
        if (accountPage.isAccountCreatedNow()) {
            new Header().navigateToHome();
            accountPage.deleteAccount();
        }
    }


}
