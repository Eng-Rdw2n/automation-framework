package com.radwanalbunni.automation.core.base;

import com.radwanalbunni.automation.core.config.ConfigReader;
import com.radwanalbunni.automation.core.driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class UiBaseTest {

    private static final Logger log = LogManager.getLogger(UiBaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        ConfigReader config = ConfigReader.getInstance();

        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();

        driver.manage().window().maximize();
        driver.get(config.getBaseUrl());
        log.info("[Setup] Browser opened: {} | URL: {}", config.getBrowser(), config.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
        log.info("[TearDown] Driver quit | Thread: {}", Thread.currentThread().getName());
    }
}
