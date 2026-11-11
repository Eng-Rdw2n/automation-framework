package com.radwanalbunni.automation.data.factory;

import java.util.UUID;

public final class UniqueDataFactory {

    private UniqueDataFactory() {
    }

    public static String email() {
        return "test_" + UUID.randomUUID() + "@test.com";
    }
}
