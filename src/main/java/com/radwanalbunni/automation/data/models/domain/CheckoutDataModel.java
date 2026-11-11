package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CheckoutDataModel extends TestDataModel implements ResolvableTestData {

    private String comment;
    private String expectedResult;
    private String expectedValidationMessage;
    private String expectedInvoiceContent;

    // Each quantity belongs to the product at the same array index.
    private int[] quantities;

    private String[] productIds;
    private String paymentCardId;

    @JsonIgnore
    private List<ProductDataModel> products;
    @JsonIgnore
    private PaymentCardDataModel paymentCard;

    // Optional values in checkout.json override the referenced card's matching fields.
    private String name;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    public CheckoutDataModel() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getExpectedInvoiceContent() {
        return expectedInvoiceContent;
    }

    public void setExpectedInvoiceContent(String expectedInvoiceContent) {
        this.expectedInvoiceContent = expectedInvoiceContent;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public void setQuantities(int[] quantities) {
        this.quantities = quantities;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public String getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(String paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    @JsonIgnore
    public List<ProductDataModel> getProducts() {
        return products;
    }

    @JsonIgnore
    public void setProducts(List<ProductDataModel> products) {
        this.products = products;
    }

    @JsonIgnore
    public PaymentCardDataModel getPaymentCard() {
        return paymentCard;
    }

    @JsonIgnore
    public void setPaymentCard(PaymentCardDataModel paymentCard) {
        this.paymentCard = paymentCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public void resolveReferences(ReferenceDataStore references) {
        if (productIds != null) {
            products = references.products(productIds);
        }
        if (paymentCardId == null) {
            return;
        }

        paymentCard = references.paymentCard(paymentCardId);
        if (name != null) {
            paymentCard.setName(name);
        }
        if (number != null) {
            paymentCard.setNumber(number);
        }
        if (expiryMonth != null) {
            paymentCard.setExpiryMonth(expiryMonth);
        }
        if (expiryYear != null) {
            paymentCard.setExpiryYear(expiryYear);
        }
        if (cvv != null) {
            paymentCard.setCvv(cvv);
        }
    }
}
