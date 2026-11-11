package com.radwanalbunni.automation.core.driver;

import com.radwanalbunni.automation.core.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    private static final ThreadLocal<WebDriver> DRIVER_HOLDER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        if (DRIVER_HOLDER.get() != null) {
            throw new IllegalStateException(
                    "Driver is already initialised for thread: " + Thread.currentThread().getName() +
                            ". Call quitDriver() before calling initDriver() again. " +
                            "This usually means @BeforeMethod is calling setup() twice via inheritance."
            );
        }

        ConfigReader config = ConfigReader.getInstance();
        String browser = config.getBrowser();
        boolean headless = config.isHeadless();

        log.info("[DriverFactory] Initialising | Browser: {} | Headless: {} | Thread: {}",
                browser, headless, Thread.currentThread().getName());

        try {
            BrowserDriver browserDriver = createBrowserDriver(browser, headless);
            WebDriver webDriver = browserDriver.create();
            DRIVER_HOLDER.set(webDriver);
            log.info("[DriverFactory] {} driver started successfully", browser);

        } catch (Exception e) {
            log.error("[DriverFactory] Failed to initialise WebDriver", e);
            throw new RuntimeException("Driver initialisation failed for browser: " + browser, e);
        }
    }

    private static BrowserDriver createBrowserDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriverCreator(headless);
            case "firefox" -> new FirefoxDriverCreator(headless);
            default -> {
                log.error("[DriverFactory] Unsupported browser: '{}'", browser);
                throw new RuntimeException(
                        "Unsupported browser: '" + browser + "'. " +
                                "Supported values: chrome, firefox"
                );
            }
        };
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = DRIVER_HOLDER.get();
        if (webDriver == null) {
            throw new IllegalStateException(
                    "Driver is not initialised for thread: " + Thread.currentThread().getName() +
                            ". Call initDriver() first (usually in @BeforeMethod)."
            );
        }
        return webDriver;
    }

    public static void quitDriver() {
        WebDriver webDriver = DRIVER_HOLDER.get();
        if (webDriver != null) {
            try {
                log.info("[DriverFactory] Quitting driver for thread: {}", Thread.currentThread().getName());
                webDriver.quit();
            } catch (Exception e) {
                log.warn("[DriverFactory] Error while quitting driver (browser may have already closed)", e);
            } finally {
                DRIVER_HOLDER.remove();
            }
        }
    }

    /**
     * Returns true if a driver is currently active for the calling thread.
     * Useful for conditional screenshot capture in contexts that may be called
     * from both UI and API tests (where no driver exists).
     */
    public static boolean isDriverActive() {
        return DRIVER_HOLDER.get() != null;
    }
}
