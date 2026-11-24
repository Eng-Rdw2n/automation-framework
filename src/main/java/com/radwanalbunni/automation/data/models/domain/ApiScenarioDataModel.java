package com.radwanalbunni.automation.data.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;

/**
 * Reusable data model for simple API scenarios such as Products and Brands.
 */
public class ApiScenarioDataModel extends TestDataModel implements ResolvableTestData {

    private String userId;
    @JsonIgnore
    private UserDataModel user;
    private String searchProduct;
    private String expectedResult;
    private int expectedHttpStatus;
    private int expectedResponseCode;

    public ApiScenarioDataModel() {
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

    @Override
    public void resolveReferences(ReferenceDataStore references) {
        if (userId != null) {
            user = references.user(userId);
        }
    }

    public String getSearchProduct() {
        return searchProduct;
    }

    public void setSearchProduct(String searchProduct) {
        this.searchProduct = searchProduct;
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
}
