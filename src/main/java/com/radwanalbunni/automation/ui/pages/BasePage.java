package com.radwanalbunni.automation.ui.pages;

import com.radwanalbunni.automation.core.config.ConfigReader;
import com.radwanalbunni.automation.core.driver.DriverFactory;
import com.radwanalbunni.automation.ui.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class BasePage {

    private static final By EXTERNAL_SURVEY_CLOSE_BUTTON =
            By.xpath("//*[@role='dialog' or contains(concat(' ', normalize-space(@class), ' '), ' modal ')]"
                    + "//*[self::button or self::a][normalize-space()='Close']");
    private static final By EXTERNAL_AD_CLOSE_TEXT = By.xpath("//*[normalize-space()='Close' and not(*)]");

    protected final WebDriver driver;
    protected final WaitUtils waitUtils;
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.waitUtils = new WaitUtils(driver, ConfigReader.getInstance().getExplicitWait());
    }

    public void click(By locator) {
        log.info("[UI ACTION] Click -> {}", locator);
        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                dismissExternalSurveyIfPresent();
                WebElement element = waitUtils.waitForClickable(locator);

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                element.click();
                log.info("[UI SUCCESS] Clicked -> {}", locator);
                return;
            } catch (StaleElementReferenceException e) {
                if (attempt == 2) {
                    log.error("[UI ERROR] Click failed after stale-element retry -> {}", locator, e);
                    throw e;
                }
                log.warn("[UI WARN] Stale element -> {} (retrying click once)", locator);
            } catch (ElementClickInterceptedException e) {
                log.warn("[UI WARN] Click intercepted -> {} (JS fallback)", locator);
                try {
                    WebElement element = waitUtils.waitForClickable(locator);
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", element);
                    log.info("[UI SUCCESS] Clicked via JS -> {}", locator);
                    return;
                } catch (Exception ex) {
                    log.error("[UI ERROR] JS click failed -> {}", locator, ex);
                    throw ex;
                }
            } catch (Exception e) {
                log.error("[UI ERROR] Click failed -> {}", locator, e);
                throw e;
            }
        }
    }

    public void type(By locator, String text) {
        log.info("[UI ACTION] Enter text -> {}", locator);
        try {
            dismissExternalSurveyIfPresent();
            WebElement element = waitUtils.waitForVisibility(locator);
            element.clear();
            element.sendKeys(text);
            log.info("[UI SUCCESS] Text entered -> {}", locator);
        } catch (Exception e) {
            log.error("[UI ERROR] Enter text failed -> {}", locator, e);
            throw e;
        }
    }

    public void uploadFile(By locator, String filePath) {
        String fileName = Paths.get(filePath).getFileName().toString();
        log.info("[UI ACTION] Upload file '{}' -> {}", fileName, locator);

        String absolutePath = resolveResourcePath(filePath);
        try {
            WebElement element = waitUtils.waitForVisibility(locator);
            element.sendKeys(absolutePath);
            log.info("[UI SUCCESS] Uploaded file '{}'", fileName);
        } catch (RuntimeException exception) {
            log.error("[UI ERROR] Upload failed '{}'", fileName, exception);
            throw exception;
        }
    }

    private String resolveResourcePath(String filePath) {
        URL resource = getClass().getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new IllegalArgumentException("Upload resource was not found: " + filePath);
        }

        try {
            return Paths.get(resource.toURI()).toString();
        } catch (URISyntaxException exception) {
            throw new IllegalStateException("Upload resource path is invalid: " + filePath, exception);
        }
    }

    public String getText(By locator) {
        log.info("[UI ACTION] GetText -> {}", locator);
        try {
            String text = waitUtils.waitForVisibility(locator).getText();
            log.info("[UI SUCCESS] Text retrieved -> {}", locator);
            return text;
        } catch (Exception e) {
            log.error("[UI ERROR] GetText failed -> {}", locator, e);
            throw e;
        }
    }

    /**
     * Returns the browser's native validation message for a form field.
     */
    public String getNativeValidationMessage(By locator) {
        WebElement element = waitUtils.waitForVisibility(locator);
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].validationMessage;", element
        );
    }

    /**
     * Checks browser-native validation without depending on locale-specific validation text.
     */
    public boolean isFieldValid(By locator) {
        WebElement element = waitUtils.waitForVisibility(locator);
        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].checkValidity();", element
        );
        return Boolean.TRUE.equals(result);
    }

    public boolean isDisplayed(By locator) {
        try {
            dismissExternalSurveyIfPresent();
            waitUtils.waitForVisibility(locator);
            log.info("[UI CHECK] Displayed -> {}", locator);
            return true;
        } catch (TimeoutException | NoSuchElementException exception) {
            log.warn("[UI CHECK] NOT displayed -> {}", locator);
            return false;
        }
    }

    public void selectByVisibleText(By locator, String text) {
        log.info("[UI ACTION] Select option -> {}", locator);
        try {
            WebElement element = waitUtils.waitForVisibility(locator);
            Select select = new Select(element);
            select.selectByVisibleText(text);
            log.info("[UI SUCCESS] Option selected -> {}", locator);
        } catch (Exception e) {
            log.error("[UI ERROR] Select option failed -> {}", locator, e);
            throw e;
        }
    }

    /**
     * Closes the known external survey overlay used by the public test site.
     */
    private void dismissExternalSurveyIfPresent() {
        try {
            for (WebElement closeButton : driver.findElements(EXTERNAL_SURVEY_CLOSE_BUTTON)) {
                if (closeButton.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
                    log.info("[UI ACTION] Dismissed external survey overlay");
                    return;
                }
            }
            for (WebElement closeButton : driver.findElements(EXTERNAL_AD_CLOSE_TEXT)) {
                Rectangle bounds = closeButton.getRect();
                Dimension viewport = driver.manage().window().getSize();
                if (closeButton.isDisplayed()
                        && bounds.getX() >= viewport.getWidth() / 2
                        && bounds.getY() <= viewport.getHeight() / 3) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
                    log.info("[UI ACTION] Dismissed external advertisement overlay");
                    return;
                }
            }
        } catch (WebDriverException exception) {
            log.debug("[UI CHECK] External survey overlay was not actionable");
        }
    }

    public void acceptAlert() {
        log.info("[UI ACTION] Accept alert");
        try {
            Alert alert = waitUtils.waitForAlert();
            log.info("[UI ALERT] Alert displayed");
            alert.accept();
            log.info("[UI SUCCESS] Alert accepted");
        } catch (Exception e) {
            log.error("[UI ERROR] Failed to accept alert", e);
            throw e;
        }
    }
}
