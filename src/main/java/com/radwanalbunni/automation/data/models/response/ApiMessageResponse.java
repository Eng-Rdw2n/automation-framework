package com.radwanalbunni.automation.data.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Response shape shared by API actions that return only a code and message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiMessageResponse {

    private int httpStatus;
    private int responseCode;
    private String message;

    public ApiMessageResponse() {
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
