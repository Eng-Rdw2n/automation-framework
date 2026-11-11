package com.radwanalbunni.automation.data.models.domain;

public class RegistrationDataModel extends TestDataModel {

    private String expectedResult;
    private String expectedDeletionResult;
    private String expectedUpdateResult;
    private String expectedDuplicateResult;
    private String expectedInvalidDeletionResult;
    private int expectedHttpStatus;
    private int expectedResponseCode;
    private int expectedDeletionResponseCode;
    private int expectedUpdateResponseCode;
    private int expectedDuplicateResponseCode;
    private int expectedInvalidDeletionResponseCode;

    private String title;
    private String name;
    private String updatedName;
    private String email;
    private String password;
    private String invalidPassword;

    private String day;
    private String month;
    private String year;

    private boolean newsletter;
    private boolean offers;

    private String firstname;
    private String lastname;
    private String company;

    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String mobileNumber;

    public RegistrationDataModel() {
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getExpectedDeletionResult() {
        return expectedDeletionResult;
    }

    public void setExpectedDeletionResult(String expectedDeletionResult) {
        this.expectedDeletionResult = expectedDeletionResult;
    }

    public String getExpectedUpdateResult() {
        return expectedUpdateResult;
    }

    public void setExpectedUpdateResult(String expectedUpdateResult) {
        this.expectedUpdateResult = expectedUpdateResult;
    }

    public String getExpectedDuplicateResult() {
        return expectedDuplicateResult;
    }

    public void setExpectedDuplicateResult(String expectedDuplicateResult) {
        this.expectedDuplicateResult = expectedDuplicateResult;
    }

    public String getExpectedInvalidDeletionResult() {
        return expectedInvalidDeletionResult;
    }

    public void setExpectedInvalidDeletionResult(String expectedInvalidDeletionResult) {
        this.expectedInvalidDeletionResult = expectedInvalidDeletionResult;
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

    public int getExpectedDeletionResponseCode() {
        return expectedDeletionResponseCode;
    }

    public void setExpectedDeletionResponseCode(int expectedDeletionResponseCode) {
        this.expectedDeletionResponseCode = expectedDeletionResponseCode;
    }

    public int getExpectedUpdateResponseCode() {
        return expectedUpdateResponseCode;
    }

    public void setExpectedUpdateResponseCode(int expectedUpdateResponseCode) {
        this.expectedUpdateResponseCode = expectedUpdateResponseCode;
    }

    public int getExpectedDuplicateResponseCode() {
        return expectedDuplicateResponseCode;
    }

    public void setExpectedDuplicateResponseCode(int expectedDuplicateResponseCode) {
        this.expectedDuplicateResponseCode = expectedDuplicateResponseCode;
    }

    public int getExpectedInvalidDeletionResponseCode() {
        return expectedInvalidDeletionResponseCode;
    }

    public void setExpectedInvalidDeletionResponseCode(int expectedInvalidDeletionResponseCode) {
        this.expectedInvalidDeletionResponseCode = expectedInvalidDeletionResponseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedName() {
        return updatedName;
    }

    public void setUpdatedName(String updatedName) {
        this.updatedName = updatedName;
    }

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

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public boolean isOffers() {
        return offers;
    }

    public void setOffers(boolean offers) {
        this.offers = offers;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
