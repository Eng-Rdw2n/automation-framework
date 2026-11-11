package com.radwanalbunni.automation.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.function.BiConsumer;

public class ResponseParser {

    public static <T> T parse(Response response, Class<T> clazz, BiConsumer<T, Integer> setHttpStatus) {
        String json = extractJson(response);
        T body = JsonPath.from(json).getObject("", clazz);
        setHttpStatus.accept(body, response.getStatusCode());
        return body;
    }

    private static String extractJson(Response response) {
        String body = response.getBody().asString();
        if (body == null || body.isEmpty()) {
            throw new IllegalStateException("Empty API response body (HTTP " + response.getStatusCode() + ")");
        }
        // Extract the JSON payload returned by the public sandbox API.
        int objectStart = body.indexOf("{");
        int arrayStart = body.indexOf("[");
        int start = objectStart == -1 ? arrayStart
                : arrayStart == -1 ? objectStart : Math.min(objectStart, arrayStart);
        int end = Math.max(body.lastIndexOf("}"), body.lastIndexOf("]"));
        if (start == -1 || end == -1 || end < start) {
            throw new IllegalStateException("Expected JSON API response, received HTTP " + response.getStatusCode());

        }
        return body.substring(start, end + 1);

    }
}
