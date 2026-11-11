package com.radwanalbunni.automation.core.driver;

import org.openqa.selenium.WebDriver;

/**
 * Creates a WebDriver configured for one browser type.
 */
public interface BrowserDriver {
    WebDriver create();
}
