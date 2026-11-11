package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Login scenario data backed by a reusable user record.
 */
public class LoginDataModel extends TestDataModel implements ResolvableTestData {

    // Optional JSON values override the referenced user's credentials.
    private String email;
    private String password;

    private String expectedResult;
    private int expectedHttpStatus;
    private int expectedResponseCode;

    private String userId;

    @JsonIgnore
    private UserDataModel user;

    public LoginDataModel() {
    }

    /**
     * Scenario-level credential override; the UI test uses the resolved user object.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public int getExpectedResponseCode() {
        return expectedResponseCode;
    }

    public int getExpectedHttpStatus() {
        return expectedHttpStatus;
    }

    public void setExpectedHttpStatus(int expectedHttpStatus) {
        this.expectedHttpStatus = expectedHttpStatus;
    }

    public void setExpectedResponseCode(int expectedResponseCode) {
        this.expectedResponseCode = expectedResponseCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public UserDataModel getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(UserDataModel user) {
        this.user = user;
    }

    @Override
    public void resolveReferences(ReferenceDataStore references) {
        if (userId == null) {
            return;
        }

        user = references.user(userId);
        if (email != null) {
            user.setEmail(email);
        }
        if (password != null) {
            user.setPassword(password);
        }
    }
}
