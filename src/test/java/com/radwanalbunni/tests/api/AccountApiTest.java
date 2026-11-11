package com.radwanalbunni.tests.api;

import com.radwanalbunni.automation.api.clients.AccountClient;
import com.radwanalbunni.automation.assertions.ApiAssertions;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.data.models.domain.ApiScenarioDataModel;
import com.radwanalbunni.automation.data.models.domain.RegistrationDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.models.response.UserDetailsResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

@Epic("Account API")
@Feature("Account Management")
public class AccountApiTest {

    private final AccountClient accountClient = new AccountClient();

    @Story("Get user details by email")
    @TestDataFile("api/accounts")
    @TestDataType("getUserDetail")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testGetUserDetail(ApiScenarioDataModel data) {
        UserDetailsResponse response = accountClient.getUserDetail(data.getEmail());

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertFieldEquals(response.getUser().getEmail(), data.getEmail(), "user.email");
    }

    @Story("Delete account")
    @Severity(SeverityLevel.NORMAL)
    @TestDataFile("api/registration")
    @TestDataType("validRegistration")
    @Test(groups = "smoke", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testDeleteAccount(RegistrationDataModel data) {
        ApiMessageResponse createResponse = accountClient.createAccount(data);
        ApiAssertions.assertHttpStatus(createResponse.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(createResponse.getResponseCode(), data.getExpectedResponseCode());

        ApiMessageResponse deleteResponse = accountClient.deleteAccount(data);
        ApiAssertions.assertHttpStatus(deleteResponse.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(
                deleteResponse.getResponseCode(), data.getExpectedDeletionResponseCode()
        );
        ApiAssertions.assertMessage(deleteResponse.getMessage(), data.getExpectedDeletionResult());
    }

    @Story("Reject account deletion with invalid password")
    @TestDataFile("api/registration")
    @TestDataType("validRegistration")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testDeleteAccountWithInvalidPassword(RegistrationDataModel data) {
        ApiMessageResponse createResponse = accountClient.createAccount(data);
        ApiAssertions.assertHttpStatus(createResponse.getHttpStatus(), data.getExpectedHttpStatus());
        boolean accountCreated = createResponse.getResponseCode() == data.getExpectedResponseCode();
        String validPassword = data.getPassword();

        try {
            ApiAssertions.assertBodyResponseCode(createResponse.getResponseCode(), data.getExpectedResponseCode());

            data.setPassword(data.getInvalidPassword());
            ApiMessageResponse deleteResponse = accountClient.deleteAccount(data);
            ApiAssertions.assertHttpStatus(deleteResponse.getHttpStatus(), data.getExpectedHttpStatus());

            ApiAssertions.assertBodyResponseCode(
                    deleteResponse.getResponseCode(), data.getExpectedInvalidDeletionResponseCode()
            );
            ApiAssertions.assertMessage(deleteResponse.getMessage(), data.getExpectedInvalidDeletionResult());
        } finally {
            data.setPassword(validPassword);
            if (accountCreated) {
                accountClient.deleteAccount(data);
            }
        }
    }

    @Story("Update account")
    @TestDataFile("api/registration")
    @TestDataType("validRegistration")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testUpdateAccount(RegistrationDataModel data) {
        ApiMessageResponse createResponse = accountClient.createAccount(data);
        ApiAssertions.assertHttpStatus(createResponse.getHttpStatus(), data.getExpectedHttpStatus());
        boolean accountCreated = createResponse.getResponseCode() == data.getExpectedResponseCode();

        try {
            ApiAssertions.assertBodyResponseCode(createResponse.getResponseCode(), data.getExpectedResponseCode());

            data.setName(data.getUpdatedName());
            ApiMessageResponse updateResponse = accountClient.updateAccount(data);
            ApiAssertions.assertHttpStatus(updateResponse.getHttpStatus(), data.getExpectedHttpStatus());

            ApiAssertions.assertBodyResponseCode(
                    updateResponse.getResponseCode(), data.getExpectedUpdateResponseCode()
            );
            ApiAssertions.assertMessage(updateResponse.getMessage(), data.getExpectedUpdateResult());
        } finally {
            if (accountCreated) {
                accountClient.deleteAccount(data);
            }
        }
    }
}
