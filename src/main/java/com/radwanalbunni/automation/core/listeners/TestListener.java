package com.radwanalbunni.automation.core.listeners;

import com.radwanalbunni.automation.core.logging.LoggingContext;
import com.radwanalbunni.automation.core.logging.TestLogger;
import com.radwanalbunni.automation.core.reporting.AllureAttachments;
import com.radwanalbunni.automation.core.reporting.AllureReportSetup;
import com.radwanalbunni.automation.core.retry.FailureClassifier;
import com.radwanalbunni.automation.core.retry.FailureType;
import com.radwanalbunni.automation.core.screenshot.ScreenshotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Coordinates TestNG lifecycle logging and final Allure evidence.
 */
public final class TestListener implements ITestListener, ISuiteListener, IConfigurationListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static final String LOG_FILE_ATTRIBUTE = "logFileName";
    private static final long MAX_LOG_SIZE_BYTES = 1_000_000;
    private final FailureClassifier failureClassifier = new FailureClassifier();
    private final ThreadLocal<ConfigurationFailureEvidence> configurationFailureEvidence = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        AllureReportSetup.write();
        TestLogger.logSuiteStart(suite);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String logFileName = buildLogFileName(result);
        result.setAttribute(LOG_FILE_ATTRIBUTE, logFileName);
        LoggingContext.setLogFileName(logFileName);

        TestLogger.logTestStart(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TestLogger.logTestSuccess(result);
        LoggingContext.clear();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        FailureType failureType = failureClassifier.classify(result);
        TestLogger.logTestFailure(result, failureType);
        AllureAttachments.attachText("Failure type", failureType.name());
        attachFailureScreenshot();
        attachCurrentTestLog(result);
        LoggingContext.clear();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestLogger.logTestSkipped(result);
        attachConfigurationFailureEvidence();
        attachCurrentTestLog(result);
        LoggingContext.clear();
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        FailureType failureType = failureClassifier.classify(result);
        TestLogger.logConfigurationFailure(result, failureType);
        configurationFailureEvidence.set(new ConfigurationFailureEvidence(
                failureType,
                ScreenshotService.capture()
        ));
        LoggingContext.clear();
    }

    @Override
    public void onFinish(ISuite suite) {
        TestLogger.logSuiteFinish(suite);
        configurationFailureEvidence.remove();
    }

    /**
     * Captures UI evidence while the failed test and its browser are still active.
     */
    private void attachFailureScreenshot() {
        byte[] screenshot = ScreenshotService.capture();
        if (screenshot.length > 0) {
            AllureAttachments.attachScreenshot("Failure screenshot", screenshot);
        }
    }

    private void attachCurrentTestLog(ITestResult result) {
        Object attribute = result.getAttribute(LOG_FILE_ATTRIBUTE);
        if (!(attribute instanceof String logFileName)) {
            return;
        }

        attachLogFile("Test log", new File("target/logs", logFileName + ".log"));
    }

    /**
     * Attaches setup/cleanup failure evidence after TestNG opens the skipped test in Allure.
     */
    private void attachConfigurationFailureEvidence() {
        ConfigurationFailureEvidence evidence = configurationFailureEvidence.get();
        if (evidence == null) {
            return;
        }

        AllureAttachments.attachText("Configuration failure type", evidence.failureType().name());
        if (evidence.screenshot().length > 0) {
            AllureAttachments.attachScreenshot("Configuration failure screenshot", evidence.screenshot());
        }
        attachLogFile("Configuration log", new File("target/logs/framework.log"));
        configurationFailureEvidence.remove();
    }

    private void attachLogFile(String attachmentName, File logFile) {
        if (!logFile.isFile() || logFile.length() == 0) {
            return;
        }

        try {
            byte[] content = Files.readAllBytes(logFile.toPath());
            if (content.length > MAX_LOG_SIZE_BYTES) {
                byte[] truncated = new byte[(int) MAX_LOG_SIZE_BYTES];
                System.arraycopy(content, 0, truncated, 0, truncated.length);
                content = truncated;
            }
            AllureAttachments.attachLog(attachmentName, content);
        } catch (IOException exception) {
            log.warn("[Allure] Could not attach log file: {}", logFile, exception);
        }
    }

    private String buildLogFileName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "_" + result.getMethod().getMethodName()
                + "_thread-" + Thread.currentThread().threadId()
                + "_" + Long.toUnsignedString(System.nanoTime());
    }

    private record ConfigurationFailureEvidence(FailureType failureType, byte[] screenshot) {
    }
}
