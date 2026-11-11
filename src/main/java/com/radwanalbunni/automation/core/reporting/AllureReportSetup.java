package com.radwanalbunni.automation.core.reporting;

import com.radwanalbunni.automation.core.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Prepares environment metadata and custom categories for an Allure test run.
 */
public final class AllureReportSetup {

    private static final Logger log = LogManager.getLogger(AllureReportSetup.class);

    private AllureReportSetup() {
    }

    public static void write() {
        ConfigReader config = ConfigReader.getInstance();
        File resultsDirectory = new File(System.getProperty(
                "allure.results.directory", "target/allure-results"
        ));

        if (!resultsDirectory.exists() && !resultsDirectory.mkdirs()) {
            log.warn("[Allure] Could not create results directory: {}", resultsDirectory.getAbsolutePath());
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("Environment", config.getEnv());
        properties.setProperty("Base URL", config.getBaseUrl());
        properties.setProperty("Browser", config.getBrowser());
        properties.setProperty("Headless", String.valueOf(config.isHeadless()));
        properties.setProperty("Java Version", System.getProperty("java.version"));
        properties.setProperty("OS", System.getProperty("os.name"));

        try (Writer writer = new FileWriter(new File(resultsDirectory, "environment.properties"))) {
            properties.store(writer, null);
        } catch (IOException exception) {
            log.warn("[Allure] Could not write environment metadata", exception);
        }

        copyCategories(resultsDirectory);
    }

    private static void copyCategories(File resultsDirectory) {
        try (InputStream categories = AllureReportSetup.class.getClassLoader()
                .getResourceAsStream("categories.json")) {
            if (categories == null) {
                log.warn("[Allure] categories.json resource was not found");
                return;
            }

            Files.copy(
                    categories,
                    new File(resultsDirectory, "categories.json").toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException exception) {
            log.warn("[Allure] Could not copy custom categories into results", exception);
        }
    }
}
