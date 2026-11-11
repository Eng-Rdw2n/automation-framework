package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReviewDataModel extends TestDataModel implements ResolvableTestData {

    private String review;
    private String expectedResult;
    private String expectedValidationMessage;
    // Scenario name/email override the shared user when a specific input needs testing.
    private String name;
    // Scenario email overrides the shared user's email when a format needs testing.
    private String email;

    private String productId;

    @JsonIgnore
    private ProductDataModel product;

    private String userId;

    @JsonIgnore
    private UserDataModel user;

    public ReviewDataModel() {
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonIgnore
    public ProductDataModel getProduct() {
        return product;
    }

    @JsonIgnore
    public void setProduct(ProductDataModel product) {
        this.product = product;
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


    public String getReview() {
        return review;
    }


    public void setReview(String review) {
        this.review = review;
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
        if (productId != null) {
            product = references.product(productId);
        }
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
