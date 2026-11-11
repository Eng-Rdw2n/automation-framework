package com.radwanalbunni.automation.core.retry;

/**
 * Describes whether a failed test is safe to retry.
 */
public enum FailureType {
    ASSERTION_FAILURE,
    RETRYABLE_INFRASTRUCTURE,
    NON_RETRYABLE_INFRASTRUCTURE,
    TEST_CODE_ERROR
}
