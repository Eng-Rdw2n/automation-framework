package com.radwanalbunni.automation.ui.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private final WebDriverWait defaultWait;

    public WaitUtils(WebDriver driver, long timeoutInSeconds) {
        this.defaultWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public WebElement waitForVisibility(By locator) {
        return defaultWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return defaultWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForInvisibility(By locator) {
        return defaultWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public Alert waitForAlert() {
        return defaultWait.until(ExpectedConditions.alertIsPresent());
    }
}
