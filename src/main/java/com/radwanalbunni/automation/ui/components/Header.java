package com.radwanalbunni.automation.ui.components;

import com.radwanalbunni.automation.ui.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public class Header extends BasePage {

    // Locators for header elements
    private static final By HOME_LINK = By.cssSelector("a[href='/']");
    private static final By PRODUCTS_LINK = By.cssSelector("a[href='/products']");
    private static final By CART_LINK = By.cssSelector("a[href='/view_cart']");
    private static final By LOGIN_SIGNUP_LINK = By.cssSelector("a[href='/login']");
    private static final By CONTACT_US_LINK = By.cssSelector("a[href='/contact_us']");

    @Step("Navigate to Home page")
    public void navigateToHome() {
        click(HOME_LINK);
    }

    @Step("Navigate to Products page")
    public void navigateToProducts() {
        click(PRODUCTS_LINK);
        try {
            waitUtils.waitForVisibility(By.id("search_product"));
        } catch (TimeoutException exception) {
            log.warn("[UI WARN] Products page did not open after click; current URL: {}. Retrying navigation once", driver.getCurrentUrl());
            click(PRODUCTS_LINK);
            waitUtils.waitForVisibility(By.id("search_product"));
        }
    }

    @Step("Navigate to Cart page")
    public void navigateToCart() {
        click(CART_LINK);
    }

    @Step("Navigate to Login/Signup page")
    public void navigateToLoginSignup() {
        click(LOGIN_SIGNUP_LINK);
    }

    @Step("Navigate to Contact Us page")
    public void navigateToContactUs() {
        click(CONTACT_US_LINK);
    }

}
