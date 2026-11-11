package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data for one product-discovery scenario: search bar, category, or brand.
 */
public class SearchDataModel extends TestDataModel implements ResolvableTestData {

    private String searchKeyword;
    private String expectedResult;

    private String productId;

    @JsonIgnore
    private ProductDataModel product;

    public SearchDataModel() {
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonIgnore
    public ProductDataModel getProduct() {
        return product;
    }

    @JsonIgnore
    public void setProduct(ProductDataModel product) {
        this.product = product;
    }

    @Override
    public void resolveReferences(ReferenceDataStore references) {
        if (productId != null) {
            product = references.product(productId);
        }
    }
}
