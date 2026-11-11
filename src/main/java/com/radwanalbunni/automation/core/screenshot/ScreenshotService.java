package com.radwanalbunni.automation.core.screenshot;

import com.radwanalbunni.automation.core.driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Captures a UI screenshot from the current test thread.
 */
public final class ScreenshotService {

    private static final Logger log = LogManager.getLogger(ScreenshotService.class);

    private ScreenshotService() {
    }

    public static byte[] capture() {
        if (!DriverFactory.isDriverActive()) {
            return new byte[0];
        }

        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            log.info("[Screenshot] Captured {} bytes", screenshot.length);
            return screenshot;
        } catch (Exception exception) {
            log.warn("[Screenshot] Capture failed: {}", exception.getMessage());
            return new byte[0];
        }
    }
}
