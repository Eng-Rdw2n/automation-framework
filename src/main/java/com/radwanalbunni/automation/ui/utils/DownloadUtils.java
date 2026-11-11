package com.radwanalbunni.automation.ui.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utilities for browser downloads created during UI tests.
 */
public final class DownloadUtils {

    private static final Duration DOWNLOAD_TIMEOUT = Duration.ofSeconds(20);

    private DownloadUtils() {
    }

    /**
     * Each parallel test thread receives its own download folder.
     */
    public static Path getDownloadDirectory() {
        String threadName = Thread.currentThread().getName()
                .replaceAll("[^a-zA-Z0-9._-]", "_");
        return Path.of("target", "downloads", threadName).toAbsolutePath();
    }

    /**
     * Removes files downloaded by an earlier run of the current test thread.
     */
    public static void clearDownloads() {
        Path directory = getDownloadDirectory();
        try {
            Files.createDirectories(directory);
            try (Stream<Path> files = Files.list(directory)) {
                files.forEach(DownloadUtils::deleteFile);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Could not clear download directory: " + directory, exception);
        }
    }

    /**
     * Waits for Chrome to finish downloading a text file.
     */
    public static Path waitForTextFile() {
        Path directory = getDownloadDirectory();
        Instant deadline = Instant.now().plus(DOWNLOAD_TIMEOUT);

        while (Instant.now().isBefore(deadline)) {
            try (Stream<Path> files = Files.list(directory)) {
                Optional<Path> downloadedFile = files
                        .filter(Files::isRegularFile)
                        .filter(file -> file.getFileName().toString().endsWith(".txt"))
                        .findFirst();

                if (downloadedFile.isPresent()) {
                    return downloadedFile.get();
                }
            } catch (IOException exception) {
                throw new RuntimeException("Could not inspect download directory: " + directory, exception);
            }

            pauseBriefly();
        }

        throw new RuntimeException("No text file was downloaded within "
                + DOWNLOAD_TIMEOUT.toSeconds() + " seconds.");
    }

    public static String readTextFile(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException exception) {
            throw new RuntimeException("Could not read downloaded file: " + file, exception);
        }
    }

    private static void deleteFile(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException exception) {
            throw new RuntimeException("Could not delete old download: " + file, exception);
        }
    }

    private static void pauseBriefly() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for a download.", exception);
        }
    }
}
