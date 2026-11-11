package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data for one newsletter subscription scenario using a shared user.
 */
public class SubscriptionDataModel extends TestDataModel implements ResolvableTestData {
    private String expectedResult;
    private String expectedValidationMessage;
    // Scenario email overrides the shared user's email when a format needs testing.
    private String email;

    private String userId;

    @JsonIgnore
    private UserDataModel user;

    public SubscriptionDataModel() {
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getExpectedValidationMessage() {
        return expectedValidationMessage;
    }

    public void setExpectedValidationMessage(String expectedValidationMessage) {
        this.expectedValidationMessage = expectedValidationMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    }
}
