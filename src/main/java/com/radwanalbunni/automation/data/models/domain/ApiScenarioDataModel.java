package com.radwanalbunni.automation.data.models.domain;

/**
 * Reusable data model for simple API scenarios such as Products and Brands.
 */
public class ApiScenarioDataModel extends TestDataModel {

    private String email;
    private String searchProduct;
    private String expectedResult;
    private int expectedHttpStatus;
    private int expectedResponseCode;

    public ApiScenarioDataModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
