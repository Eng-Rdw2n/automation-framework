package com.radwanalbunni.automation.data.models.domain;

public class PaymentCardDataModel {

    private String id;
    private String name;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    public PaymentCardDataModel() {
    }

    public PaymentCardDataModel(PaymentCardDataModel other) {
        this.id = other.id;
        this.name = other.name;
        this.number = other.number;
        this.expiryMonth = other.expiryMonth;
        this.expiryYear = other.expiryYear;
        this.cvv = other.cvv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
