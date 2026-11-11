package com.radwanalbunni.tests.api;

import com.radwanalbunni.automation.api.clients.AccountClient;
import com.radwanalbunni.automation.assertions.ApiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.data.models.domain.RegistrationDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.GenericDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Registration API")
public class RegistrationApiTest {

    private final AccountClient accountClient = new AccountClient();

    @Story("Create account")
    @Severity(SeverityLevel.CRITICAL)
    @TestDataFile("api/registration")
    @TestDataType("validRegistration")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testCreateAccount(RegistrationDataModel data) {
        ApiMessageResponse response = accountClient.createAccount(data);

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertMessage(response.getMessage(), data.getExpectedResult());
    }

    @Story("Reject duplicate account email")
    @TestDataFile("api/registration")
    @TestDataType("validRegistration")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testRegistrationWithDuplicateEmail(RegistrationDataModel data) {
        ApiMessageResponse firstResponse = accountClient.createAccount(data);
        ApiAssertions.assertHttpStatus(firstResponse.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(firstResponse.getResponseCode(), data.getExpectedResponseCode());

        ApiMessageResponse duplicateResponse = accountClient.createAccount(data);
        ApiAssertions.assertHttpStatus(duplicateResponse.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(
                duplicateResponse.getResponseCode(), data.getExpectedDuplicateResponseCode()
        );
        ApiAssertions.assertMessage(duplicateResponse.getMessage(), data.getExpectedDuplicateResult());
    }

    /**
     * Keeps the public sandbox clean without adding deletion steps to the creation test.
     */
    @AfterMethod(alwaysRun = true)
    public void deleteCreatedAccount(ITestResult result) {
        RegistrationDataModel data = (RegistrationDataModel) result.getParameters()[0];
        accountClient.deleteAccount(data);
    }
}
