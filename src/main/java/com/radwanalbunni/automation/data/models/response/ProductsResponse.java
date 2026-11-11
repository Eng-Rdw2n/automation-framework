package com.radwanalbunni.automation.data.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response shape returned by the products and product-search endpoints.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsResponse {

    private int httpStatus;
    private int responseCode;
    private List<?> products;

    public ProductsResponse() {
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

    public List<?> getProducts() {
        return products;
    }

    public void setProducts(List<?> products) {
        this.products = products;
    }
}
