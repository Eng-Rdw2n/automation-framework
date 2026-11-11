package com.radwanalbunni.automation.data;

/**
 * Implemented by scenario models that reference reusable shared test data.
 */
public interface ResolvableTestData {

    void resolveReferences(ReferenceDataStore references);
}
