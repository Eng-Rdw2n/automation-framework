package com.radwanalbunni.automation.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radwanalbunni.automation.data.factory.UniqueDataFactory;

import java.lang.reflect.Field;

public final class DataProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ReferenceDataStore REFERENCES = new ReferenceDataStore();

    private DataProcessor() {
    }

    public static <T> T process(T data) {
        if (data == null) {
            return null;
        }

        try {
            T copy = deepCopy(data);
            processDynamicValues(copy);
            if (copy instanceof ResolvableTestData resolvable) {
                resolvable.resolveReferences(REFERENCES);
            }
            return copy;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to process test data: " + data.getClass().getSimpleName(),
                    e
            );
        }
    }

    private static void processDynamicValues(Object data) throws IllegalAccessException {
        for (Field field : data.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(data);

            if (!(value instanceof String stringValue)) {
                continue;
            }

            field.set(data, processValue(stringValue));
        }
    }

    private static String processValue(String value) {
        if (value == null) {
            return null;
        }

        if (value.contains("{random}")) {
            value = value.replace("{random}", UniqueDataFactory.email());
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    private static <T> T deepCopy(T data) {
        try {
            return MAPPER.readValue(MAPPER.writeValueAsString(data), (Class<T>) data.getClass());
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy test data", e);
        }
    }
}
