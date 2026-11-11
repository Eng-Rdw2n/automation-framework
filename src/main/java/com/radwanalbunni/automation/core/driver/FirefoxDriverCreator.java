package com.radwanalbunni.automation.core.driver;

import com.radwanalbunni.automation.ui.utils.DownloadUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public final class FirefoxDriverCreator implements BrowserDriver {

    private final boolean headless;

    public FirefoxDriverCreator(boolean headless) {
        this.headless = headless;
    }

    @Override
    public WebDriver create() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", DownloadUtils.getDownloadDirectory().toString());
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "text/plain,application/octet-stream");
        options.setProfile(profile);

        if (headless) {
            options.addArguments("--headless");
        }
        return new FirefoxDriver(options);
    }
}
