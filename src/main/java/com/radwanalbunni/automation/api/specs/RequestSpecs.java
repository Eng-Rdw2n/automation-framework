package com.radwanalbunni.automation.api.specs;

import com.radwanalbunni.automation.core.config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification getSpec() {

        return new RequestSpecBuilder()
                // One API base URL for every client; read from the selected environment config.
                .setBaseUri(ConfigReader.getInstance().getBaseUrl())
                .setContentType("application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
    }
}
