package com.radwanalbunni.automation.core.retry;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Classifies failures. It has no logging, reporting, or retry side effects.
 */
public final class FailureClassifier {

    public FailureType classify(ITestResult result) {
        return classify(result.getThrowable());
    }

    public FailureType classify(Throwable throwable) {
        for (Throwable current = throwable; current != null; current = current.getCause()) {
            if (current instanceof AssertionError) {
                return FailureType.ASSERTION_FAILURE;
            }

            // A missing element usually indicates a locator or test-design problem.
            if (current instanceof NoSuchElementException) {
                return FailureType.NON_RETRYABLE_INFRASTRUCTURE;
            }

            if (current instanceof InvalidSelectorException
                    || current instanceof InvalidArgumentException
                    || current instanceof NoSuchSessionException
                    || current instanceof SessionNotCreatedException) {
                return FailureType.NON_RETRYABLE_INFRASTRUCTURE;
            }

            if (current instanceof SocketTimeoutException
                    || current instanceof ConnectException
                    || current instanceof TimeoutException
                    || current instanceof StaleElementReferenceException
                    || current instanceof ElementClickInterceptedException) {
                return FailureType.RETRYABLE_INFRASTRUCTURE;
            }

            if (current instanceof WebDriverException) {
                return FailureType.NON_RETRYABLE_INFRASTRUCTURE;
            }
        }
        return FailureType.TEST_CODE_ERROR;
    }
}
