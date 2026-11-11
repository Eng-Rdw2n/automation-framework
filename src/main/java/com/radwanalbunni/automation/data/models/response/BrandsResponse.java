package com.radwanalbunni.automation.data.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response shape returned by the brands' endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandsResponse {

    private int httpStatus;
    private int responseCode;
    private List<?> brands;

    public BrandsResponse() {
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

    public List<?> getBrands() {
        return brands;
    }

    public void setBrands(List<?> brands) {
        this.brands = brands;
    }
}
