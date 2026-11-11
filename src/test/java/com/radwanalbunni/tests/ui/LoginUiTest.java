package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.data.models.domain.LoginDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.AccountPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login UI")
public class LoginUiTest extends UiBaseTest {

    @Story("Valid login")
    @Severity(SeverityLevel.CRITICAL)
    @TestDataType("validLogin")
    @TestDataFile("ui/login")
    @Description("Verify user can log in with valid credentials")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testValidLogin(LoginDataModel data) {
        AccountPage accountPage = new AccountPage();
        Header header = new Header();
        header.navigateToLoginSignup();
        accountPage.login(data.getUser());
        UiAssertions.assertLoggedInAs(
                accountPage.getLoggedInText(),
                data.getUser().getName()
        );

    }

    @Story("Invalid login")
    @TestDataType("wrongCombination")
    @TestDataFile("ui/login")
    @Description("Verify user cannot log in with invalid credentials")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testInvalidLogin(LoginDataModel data) {
        AccountPage accountPage = new AccountPage();
        Header header = new Header();

        header.navigateToLoginSignup();
        accountPage.login(data.getUser());

        UiAssertions.assertTextEquals(
                accountPage.getLoginErrorMessage(),
                data.getExpectedResult(),
                "Invalid login message"
        );
    }

    @Story("Validate required login credentials")
    @TestDataType("emptyField")
    @TestDataFile("ui/login")
    @Description("Verify login does not submit when email or password is empty")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testLoginRequiresEmailAndPassword(LoginDataModel data) {
        AccountPage accountPage = new AccountPage();
        Header header = new Header();

        header.navigateToLoginSignup();
        accountPage.login(data.getUser());

        String validationMessage = data.getUser().getEmail().isEmpty()
                ? accountPage.getLoginEmailValidationMessage()
                : accountPage.getLoginPasswordValidationMessage();

        UiAssertions.assertTextEquals(
                validationMessage,
                data.getExpectedResult(),
                "Required login credential validation message"
        );
    }

    @Story("Reject unusually long login email")
    @TestDataType("edgeCase")
    @TestDataFile("ui/login")
    @Description("Verify an unregistered long email cannot log in")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testLoginWithLongEmailIsRejected(LoginDataModel data) {
        AccountPage accountPage = new AccountPage();
        Header header = new Header();

        header.navigateToLoginSignup();
        accountPage.login(data.getUser());

        UiAssertions.assertTextEquals(
                accountPage.getLoginErrorMessage(),
                data.getExpectedResult(),
                "Invalid login message"
        );
    }

    @Story("Logout after valid login")
    @Severity(SeverityLevel.NORMAL)
    @TestDataType("validLogin")
    @TestDataFile("ui/login")
    @Description("Verify a logged-in user can log out successfully")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testLogoutAfterValidLogin(LoginDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();

        header.navigateToLoginSignup();
        accountPage.login(data.getUser());
        accountPage.logout();

        UiAssertions.assertTrue(
                accountPage.isLoggedOut(),
                "User should be logged out and the login/signup link should be visible"
        );
    }

}
