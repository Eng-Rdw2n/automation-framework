package com.radwanalbunni.automation.assertions;

import org.testng.Assert;

import java.util.List;


public final class ApiAssertions {

    private ApiAssertions() {
    }

    public static void assertHttpStatus(int actual, int expected) {
        Assert.assertEquals(actual, expected,
                "HTTP status mismatch.");
    }

    public static void assertBodyResponseCode(int actual, int expected) {
        Assert.assertEquals(actual, expected,
                "API body responseCode mismatch.");
    }

    public static void assertMessage(String actual, String expected) {
        Assert.assertEquals(actual, expected,
                "API message mismatch.");
    }

    public static void assertFieldEquals(String actual, String expected, String fieldName) {
        Assert.assertEquals(actual, expected,
                "API field '" + fieldName + "' mismatch.");
    }

    public static void assertListNotEmpty(List<?> values, String listName) {
        Assert.assertNotNull(values,
                "Expected list '" + listName + "' was not found in the response body.");
        Assert.assertFalse(values.isEmpty(),
                "Expected list '" + listName + "' to contain at least one item.");
    }

    public static void assertListEmpty(List<?> values, String listName) {
        Assert.assertNotNull(values,
                "Expected list '" + listName + "' was not found in the response body.");
        Assert.assertTrue(values.isEmpty(),
                "Expected list '" + listName + "' to be empty, but it contained " + values.size() + " item(s).");
    }
}
