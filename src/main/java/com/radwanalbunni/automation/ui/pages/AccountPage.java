package com.radwanalbunni.automation.ui.pages;

import com.radwanalbunni.automation.data.models.domain.RegistrationDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AccountPage extends BasePage {

    // Login locators
    private static final By LOGIN_EMAIL = By.cssSelector("input[data-qa='login-email']");
    private static final By LOGIN_PASSWORD = By.cssSelector("input[data-qa='login-password']");
    private static final By LOGIN_BUTTON = By.cssSelector("button[data-qa='login-button']");

    // Signup locators
    private static final By SIGNUP_NAME = By.cssSelector("input[data-qa='signup-name']");
    private static final By SIGNUP_EMAIL = By.cssSelector("input[data-qa='signup-email']");
    private static final By SIGNUP_BUTTON = By.cssSelector("button[data-qa='signup-button']");
    private static final By SIGNUP_AND_LOGIN_BUTTON = By.cssSelector("a[href='/login']");

    // Account action locators
    private static final By DELETE_ACCOUNT_BUTTON = By.xpath("//a[contains(normalize-space(.),'Delete Account')]");
    private static final By LOGOUT_BUTTON = By.xpath("//a[contains(normalize-space(.),'Logout')]");
    private static final By LOGGED_IN_USER = By.xpath("//a[contains(normalize-space(.),'Logged in as')]");
    private static final By LOGIN_ERROR_MESSAGE = By.xpath("//p[normalize-space()='Your email or password is incorrect!']");
    private static final By SIGNUP_EMAIL_EXISTS_MESSAGE = By.xpath("//p[normalize-space()='Email Address already exist!']");

    // Registration form locators
    private static final By TITLE_MR = By.id("id_gender1");
    private static final By TITLE_MRS = By.id("id_gender2");
    private static final By SIGNUP_PASSWORD = By.id("password");
    private static final By DAYS = By.id("days");
    private static final By MONTHS = By.id("months");
    private static final By YEARS = By.id("years");
    private static final By NEWSLETTER_CHECKBOX = By.id("newsletter");
    private static final By OFFERS_CHECKBOX = By.id("optin");

    // Address form locators
    private static final By FIRST_NAME = By.id("first_name");
    private static final By LAST_NAME = By.id("last_name");
    private static final By COMPANY = By.id("company");
    private static final By ADDRESS_1 = By.id("address1");
    private static final By ADDRESS_2 = By.id("address2");
    private static final By COUNTRY_DROPDOWN = By.id("country");
    private static final By STATE = By.id("state");
    private static final By CITY = By.id("city");
    private static final By ZIPCODE = By.id("zipcode");
    private static final By MOBILE_NUMBER = By.id("mobile_number");
    private static final By CREATE_ACCOUNT_BUTTON = By.cssSelector("button[data-qa='create-account']");

    // Result locators
    private static final By ACCOUNT_DELETED_MESSAGE = By.cssSelector("[data-qa='account-deleted']");
    private static final By ACCOUNT_CREATED_MESSAGE = By.cssSelector("[data-qa='account-created']");

    @Step("Login with user credentials")
    public void login(UserDataModel data) {
        login(data.getEmail(), data.getPassword());
    }

    // Supports data models that resolve credentials into flat email/password fields.
    @Step("Login with user credentials")
    public void login(String email, String password) {
        type(LOGIN_EMAIL, email);
        type(LOGIN_PASSWORD, password);
        click(LOGIN_BUTTON);
    }

    @Step("Start account signup")
    public void signup(RegistrationDataModel data) {
        type(SIGNUP_NAME, data.getName());
        type(SIGNUP_EMAIL, data.getEmail());
        click(SIGNUP_BUTTON);
    }

    @Step("Logout")
    public void logout() {
        click(LOGOUT_BUTTON);
    }

    @Step("Delete account")
    public void deleteAccount() {
        click(DELETE_ACCOUNT_BUTTON);
    }

    @Step("Fill account information")
    public void addAccountInfo(RegistrationDataModel data) {
        if ("Mr".equalsIgnoreCase(data.getTitle())) {
            click(TITLE_MR);
        } else if ("Mrs".equalsIgnoreCase(data.getTitle())) {
            click(TITLE_MRS);
        }
        type(SIGNUP_PASSWORD, data.getPassword());
        selectByVisibleText(DAYS, data.getDay());
        selectByVisibleText(MONTHS, data.getMonth());
        selectByVisibleText(YEARS, data.getYear());
        if (data.isNewsletter()) click(NEWSLETTER_CHECKBOX);
        if (data.isOffers()) click(OFFERS_CHECKBOX);
    }

    @Step("Fill account address details")
    public void fillAddressDetails(RegistrationDataModel data) {
        type(FIRST_NAME, data.getFirstname());
        type(LAST_NAME, data.getLastname());
        type(COMPANY, data.getCompany());
        type(ADDRESS_1, data.getAddress1());
        type(ADDRESS_2, data.getAddress2());
        selectByVisibleText(COUNTRY_DROPDOWN, data.getCountry());
        type(STATE, data.getState());
        type(CITY, data.getCity());
        type(ZIPCODE, data.getZipcode());
        type(MOBILE_NUMBER, data.getMobileNumber());
        click(CREATE_ACCOUNT_BUTTON);
    }

    public String getAccountDeletedMessage() {
        return getText(ACCOUNT_DELETED_MESSAGE);
    }

    public boolean isAccountCreatedNow() {
        return driver.findElements(ACCOUNT_CREATED_MESSAGE).stream()
                .anyMatch(element -> element.isDisplayed());
    }

    public String getAccountCreatedMessage() {
        return getText(ACCOUNT_CREATED_MESSAGE);
    }

    public String getLoggedInText() {
        return getText(LOGGED_IN_USER);
    }

    public boolean isLoggedOut() {
        return isDisplayed(SIGNUP_AND_LOGIN_BUTTON);
    }

    public String getLoginErrorMessage() {
        return getText(LOGIN_ERROR_MESSAGE);
    }

    /**
     * Returns the browser validation message for an empty login email field.
     */
    public String getLoginEmailValidationMessage() {
        return getNativeValidationMessage(LOGIN_EMAIL);
    }

    /**
     * Returns the browser validation message for an empty login password field.
     */
    public String getLoginPasswordValidationMessage() {
        return getNativeValidationMessage(LOGIN_PASSWORD);
    }

    public String getSignupNameValidationMessage() {
        return getNativeValidationMessage(SIGNUP_NAME);
    }

    public String getSignupEmailValidationMessage() {
        return getNativeValidationMessage(SIGNUP_EMAIL);
    }

    /**
     * Returns the message displayed when signup uses an already registered email address.
     */
    public String getSignupEmailExistsMessage() {
        return getText(SIGNUP_EMAIL_EXISTS_MESSAGE);
    }


}
