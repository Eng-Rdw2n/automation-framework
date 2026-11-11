package com.radwanalbunni.automation.core.retry;

import com.radwanalbunni.automation.core.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Makes a retry decision only. Reporting and screenshots are handled elsewhere.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
    private final FailureClassifier classifier = new FailureClassifier();
    private final int maxRetries = ConfigReader.getInstance().getMaxRetries();
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
            log.warn("[Retry] {} | scheduling retry {}/{}",
                    result.getMethod().getQualifiedName(), attempt, maxRetries);
        } else {
            log.warn("[Retry] {} | retry limit reached ({}) | no further retry",
                    result.getMethod().getQualifiedName(), maxRetries);
        }
        return willRetry;
    }
}
