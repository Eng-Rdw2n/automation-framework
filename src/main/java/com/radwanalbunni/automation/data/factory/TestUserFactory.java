package com.radwanalbunni.automation.data.factory;

import com.radwanalbunni.automation.api.clients.AccountClient;
import com.radwanalbunni.automation.data.models.domain.RegistrationDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates and deletes isolated accounts for tests that change server-side user state.
 */
public class TestUserFactory {

    private static final Logger log = LogManager.getLogger(TestUserFactory.class);
    private static final int HTTP_OK = 200;
    private static final int ACCOUNT_CREATED = 201;
    private static final int ACCOUNT_DELETED = 200;
    private final AccountClient accountClient = new AccountClient();

    public UserDataModel createUser() {
        RegistrationDataModel registration = newRegistrationData();
        ApiMessageResponse response = accountClient.createAccount(registration);

        if (response.getHttpStatus() != HTTP_OK) {
            throw new IllegalStateException(
                    "Could not create an isolated test account. HTTP status: " + response.getHttpStatus()
            );
        }

        if (response.getResponseCode() != ACCOUNT_CREATED) {
            throw new IllegalStateException(
                    "Could not create an isolated test account. API response: "
                            + response.getResponseCode() + " - " + response.getMessage()
            );
        }

        UserDataModel user = new UserDataModel();
        user.setName(registration.getName());
        user.setEmail(registration.getEmail());
        user.setPassword(registration.getPassword());
        return user;
    }

    public void deleteUser(UserDataModel user) {
        if (user != null) {
            ApiMessageResponse response = accountClient.deleteAccount(user);
            if (response.getHttpStatus() != HTTP_OK || response.getResponseCode() != ACCOUNT_DELETED) {
                log.warn("[TestUserFactory] Test account cleanup failed | HTTP: {} | responseCode: {}",
                        response.getHttpStatus(), response.getResponseCode());
            }
        }
    }

    private RegistrationDataModel newRegistrationData() {
        RegistrationDataModel data = new RegistrationDataModel();
        data.setTitle("Mr");
        data.setName("Automation Test User");
        data.setEmail(UniqueDataFactory.email());
        data.setPassword("Test@123");
        data.setDay("1");
        data.setMonth("January");
        data.setYear("1990");
        data.setFirstname("Automation");
        data.setLastname("User");
        data.setCompany("QA Automation");
        data.setAddress1("1 Test Street");
        data.setAddress2("");
        data.setCountry("India");
        data.setZipcode("12345");
        data.setState("Dubai");
        data.setCity("Dubai");
        data.setMobileNumber("+971500000000");
        return data;
    }
}
