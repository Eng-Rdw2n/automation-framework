package com.radwanalbunni.automation.core.driver;

import com.radwanalbunni.automation.ui.utils.DownloadUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public final class ChromeDriverCreator implements BrowserDriver {

    private final boolean headless;

    public ChromeDriverCreator(boolean headless) {
        this.headless = headless;
    }

    @Override
    public WebDriver create() {

        String chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");

        if (chromeDriverPath != null && !chromeDriverPath.isBlank()) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        } else {
            WebDriverManager.chromedriver().setup();
        }

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        // Docker installs Chromium at a known Linux path. Local macOS runs do not
        // set this variable, so ChromeDriver keeps its normal browser discovery.
        String chromeBinary = System.getenv("CHROME_BINARY");
        if (chromeBinary != null && !chromeBinary.isBlank()) {
            options.setBinary(chromeBinary);
        }

        Map<String, Object> downloadPreferences = new HashMap<>();
        downloadPreferences.put("download.default_directory",
                DownloadUtils.getDownloadDirectory().toString());
        downloadPreferences.put("download.prompt_for_download", false);
        downloadPreferences.put("download.directory_upgrade", true);
        downloadPreferences.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", downloadPreferences);

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        // Required for Chrome in Docker/CI environments.
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return new ChromeDriver(options);
    }
}
