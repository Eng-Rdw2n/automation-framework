package com.radwanalbunni.automation.api.clients;

import com.radwanalbunni.automation.api.endpoints.Endpoints;
import com.radwanalbunni.automation.api.specs.RequestSpecs;
import com.radwanalbunni.automation.api.ResponseParser;
import com.radwanalbunni.automation.core.reporting.AllureAttachments;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.models.response.ProductsResponse;

public class ProductClient extends ApiClientBase {

    public ProductsResponse getAllProducts() {
        log.info("[API] Get all products");
        AllureAttachments.attachText("Request", "GET /api/productsList");
        return ResponseParser.parse(get(RequestSpecs.getSpec(), Endpoints.GET_ALL_PRODUCTS), ProductsResponse.class, ProductsResponse::setHttpStatus);
    }

    public ApiMessageResponse postToProducts() {
        log.info("[API] Reject unsupported POST to products");
        AllureAttachments.attachText("Request", "POST /api/productsList");
        return ResponseParser.parse(post(RequestSpecs.getSpec(), Endpoints.GET_ALL_PRODUCTS), ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public ProductsResponse searchProducts(String searchProduct) {
        log.info("[API] Search products | search term: {}", searchProduct);
        AllureAttachments.attachText("Request", "POST /api/searchProduct | search_product=" + searchProduct);
        return ResponseParser.parse(
                post(RequestSpecs.getSpec().formParam("search_product", searchProduct), Endpoints.SEARCH_PRODUCT),
                ProductsResponse.class,
                ProductsResponse::setHttpStatus
        );
    }

    public ApiMessageResponse searchWithoutParameter() {
        log.info("[API] Search products without required parameter");
        AllureAttachments.attachText("Request", "POST /api/searchProduct | search_product parameter omitted");
        return ResponseParser.parse(post(RequestSpecs.getSpec(), Endpoints.SEARCH_PRODUCT), ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }
}
