package com.radwanalbunni.tests.api;

import com.radwanalbunni.automation.api.clients.AccountClient;
import com.radwanalbunni.automation.assertions.ApiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.data.models.domain.LoginDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.GenericDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login API")
public class LoginApiTest {

    private final AccountClient accountClient = new AccountClient();

    @Story("Valid login")
    @Severity(SeverityLevel.CRITICAL)
    @TestDataFile("api/login")
    @TestDataType("validLogin")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testValidLogin(LoginDataModel data) {
        ApiMessageResponse response = accountClient.verifyLogin(data.getUser());

        assertMessageResponse(response, data);
    }

    @Story("Invalid login")
    @TestDataFile("api/login")
    @TestDataType("wrongCombination")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testInvalidLogin(LoginDataModel data) {
        ApiMessageResponse response = accountClient.verifyLogin(data.getUser());

        assertMessageResponse(response, data);
    }

    @Story("Missing login email")
    @TestDataFile("api/login")
    @TestDataType("emptyField")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testLoginWithoutEmail(LoginDataModel data) {
        ApiMessageResponse response = accountClient.verifyLoginWithoutEmail(data.getUser());

        assertMessageResponse(response, data);
    }

    @Story("Missing login password")
    @TestDataFile("api/login")
    @TestDataType("missingPassword")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testLoginWithoutPassword(LoginDataModel data) {
        ApiMessageResponse response = accountClient.verifyLoginWithoutPassword(data.getUser());

        assertMessageResponse(response, data);
    }

    @Story("Reject unsupported login method")
    @TestDataFile("api/login")
    @TestDataType("unsupportedDelete")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testDeleteVerifyLoginIsRejected(LoginDataModel data) {
        ApiMessageResponse response = accountClient.deleteVerifyLogin();

        assertMessageResponse(response, data);
    }

    private void assertMessageResponse(ApiMessageResponse response, LoginDataModel data) {
        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertMessage(response.getMessage(), data.getExpectedResult());
    }
}
