package com.radwanalbunni.automation.assertions;

import org.testng.Assert;

public final class UiAssertions {

    private UiAssertions() {
    }

    public static void assertTextEquals(String actual, String expected, String description) {
        Assert.assertEquals(
                normalize(actual),
                normalize(expected),
                description + " mismatch."
        );
    }

    public static void assertTextContains(String actual, String expected, String description) {
        Assert.assertTrue(
                actual != null && actual.contains(expected),
                description + " should contain '" + expected + "'. Actual: '" + actual + "'"
        );
    }

    public static void assertTrue(boolean condition, String description) {
        Assert.assertTrue(condition, "Expectation failed: " + description);
    }

    public static void assertFalse(boolean condition, String description) {
        Assert.assertFalse(condition, "Expectation failed: " + description);
    }

    public static void assertLoggedInAs(String actualText, String expectedUsername) {
        String expectedText = "Logged in as " + expectedUsername;
        Assert.assertEquals(
                normalize(actualText),
                expectedText,
                "Logged-in user mismatch."
        );
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
