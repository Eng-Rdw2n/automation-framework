package com.radwanalbunni.automation.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/** Loads configuration for the selected environment. */
public class ConfigReader {

    private static volatile ConfigReader instance;
    private final Properties props = new Properties();
    private final String env;
    private static final Logger log = LogManager.getLogger(ConfigReader.class);


    private ConfigReader() {
        env = System.getProperty("env", "QA").trim().toUpperCase(Locale.ROOT);
        String fileName = "config/" + env.toLowerCase(Locale.ROOT) + ".properties";


        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Config file not found: " + fileName);
            }
            props.load(input);
            log.info("[CONFIG] Loaded environment: {}", env);
            log.info("[CONFIG] File: {}", fileName);
            log.info("[CONFIG] Resolved | Browser: {} | Headless: {}", getBrowser(), isHeadless());

        } catch (IOException e) {
            log.error("[CONFIG] Failed to load config file: {}", fileName, e);
            throw new RuntimeException("Failed to load: " + fileName, e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    public String getEnv() {
        return env;
    }

    public int getMaxRetries() {
        return Integer.parseInt(props.getProperty("retry.maxRetries", "2"));
    }

    public String getRetryBackoffPageText() {
        return props.getProperty("retry.backoffPageText", "").trim();
    }

    public int getRetryBackoffSeconds() {
        return Integer.parseInt(props.getProperty("retry.backoffSeconds", "0"));
    }

    public String getBrowser() {
        return runtimeValueOrProperty("browser", "chrome");
    }

    public String getBaseUrl() {
        return props.getProperty("baseUrl");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(runtimeValueOrProperty("headless", "false"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(props.getProperty("explicitWait", "30"));
    }

    /**
     * Command-line browser/headless values override the selected environment properties.
     */
    private String runtimeValueOrProperty(String key, String defaultValue) {
        String runtimeValue = System.getProperty(key);
        if (runtimeValue != null && !runtimeValue.isBlank()) {
            return runtimeValue;
        }
        return props.getProperty(key, defaultValue);
    }
}
