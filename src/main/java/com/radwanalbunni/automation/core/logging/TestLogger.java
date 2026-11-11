package com.radwanalbunni.automation.core.logging;

import com.radwanalbunni.automation.core.retry.FailureType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ITestResult;

/**
 * Produces consistent, human-readable TestNG lifecycle logs.
 */
public final class TestLogger {

    private static final Logger log = LogManager.getLogger(TestLogger.class);

    private TestLogger() {
    }

    public static void logSuiteStart(ISuite suite) {
        log.info("[Suite] Started: {}", suite.getName());
    }

    public static void logSuiteFinish(ISuite suite) {
        int passed = 0;
        int failed = 0;
        int skipped = 0;

        for (var suiteResult : suite.getResults().values()) {
            var context = suiteResult.getTestContext();
            passed += context.getPassedTests().size();
            failed += context.getFailedTests().size();
            skipped += context.getSkippedTests().size();
        }

        log.info("[Suite] Finished: {} | Passed: {} | Failed: {} | Skipped: {}",
                suite.getName(), passed, failed, skipped);
    }

    public static void logTestStart(ITestResult result) {
        log.info("[Test] Started: {} [thread={}]", displayName(result), Thread.currentThread().getName());
    }

    public static void logTestSuccess(ITestResult result) {
        log.info("[Test] Passed: {} ({} ms)", displayName(result), duration(result));
    }

    public static void logTestFailure(ITestResult result, FailureType failureType) {
        Throwable failure = result.getThrowable();
        log.error("[Test] Failed: {} ({} ms) | Type: {} | Cause: {}",
                displayName(result),
                duration(result),
                failureType,
                failureSummary(failure));
    }

    public static void logTestSkipped(ITestResult result) {
        log.warn("[Test] Skipped: {} ({} ms) | Reason: {}",
                displayName(result), duration(result), failureSummary(result.getThrowable()));
    }

    public static void logConfigurationFailure(ITestResult result, FailureType failureType) {
        log.error("[Configuration] Failed: {} ({} ms) | Type: {} | Cause: {}",
                displayName(result),
                duration(result),
                failureType,
                failureSummary(result.getThrowable()));
    }

    private static String displayName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "." + result.getMethod().getMethodName();
    }

    private static long duration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }

    private static String failureSummary(Throwable failure) {
        if (failure == null) {
            return "No failure details provided";
        }

        String message = failure.getMessage();
        String summary = message == null || message.isBlank()
                ? failure.getClass().getSimpleName()
                : failure.getClass().getSimpleName() + ": " + message;
        return SensitiveDataMasker.sanitizeText(summary);
    }
}
