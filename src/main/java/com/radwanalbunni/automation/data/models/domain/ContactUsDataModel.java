package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ContactUsDataModel extends TestDataModel implements ResolvableTestData {

    private String subject;
    private String message;
    // Scenario name/email override the shared user when a specific input needs testing.
    private String name;
    // Scenario email overrides the shared user's email when a format needs testing.
    private String email;
    private String filePath;
    private String expectedResult;
    private String expectedValidationMessage;

    private String userId;

    @JsonIgnore
    private UserDataModel user;

    public ContactUsDataModel() {
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    @Override
    public void resolveReferences(ReferenceDataStore references) {
        if (userId == null) {
            return;
        }

        user = references.user(userId);
        if (name != null) {
            user.setName(name);
        }
        if (email != null) {
            user.setEmail(email);
        }
    }
}
