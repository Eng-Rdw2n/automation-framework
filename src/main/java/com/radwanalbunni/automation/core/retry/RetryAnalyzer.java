package com.radwanalbunni.automation.core.retry;

import com.radwanalbunni.automation.core.config.ConfigReader;
import com.radwanalbunni.automation.core.driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Decides whether to retry and optionally pauses when a configured overload page is visible.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
    private final FailureClassifier classifier = new FailureClassifier();
    private final int maxRetries = ConfigReader.getInstance().getMaxRetries();
    private final String backoffPageText = ConfigReader.getInstance().getRetryBackoffPageText();
    private final int backoffSeconds = ConfigReader.getInstance().getRetryBackoffSeconds();
    private final AtomicInteger retryAttempts = new AtomicInteger();

    @Override
    public boolean retry(ITestResult result) {
        FailureType failureType = classifier.classify(result);
        if (failureType != FailureType.RETRYABLE_INFRASTRUCTURE) {
            return false;
        }

        int attempt = retryAttempts.incrementAndGet();
        boolean willRetry = attempt <= maxRetries;

        if (willRetry) {
            if (!waitIfOverloadPageVisible()) {
                return false;
            }
            log.warn("[Retry] {} | scheduling retry {}/{}",
                    result.getMethod().getQualifiedName(), attempt, maxRetries);
        } else {
            log.warn("[Retry] {} | retry limit reached ({}) | no further retry",
                    result.getMethod().getQualifiedName(), maxRetries);
        }
        return willRetry;
    }

    private boolean waitIfOverloadPageVisible() {
        if (backoffSeconds <= 0 || backoffPageText.isBlank() || !DriverFactory.isDriverActive()) {
            return true;
        }

        try {
            String pageSource = DriverFactory.getDriver().getPageSource();
            if (!pageSource.toLowerCase(Locale.ROOT).contains(backoffPageText.toLowerCase(Locale.ROOT))) {
                return true;
            }
        } catch (WebDriverException exception) {
            log.debug("[Retry] Could not inspect the current page for the configured overload message", exception);
            return true;
        }

        log.warn("[Retry] Overload page detected; waiting {} seconds before the next attempt", backoffSeconds);
        try {
            TimeUnit.SECONDS.sleep(backoffSeconds);
            return true;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            log.warn("[Retry] Interrupted while waiting for the overload page to clear");
            return false;
        }
    }
}
