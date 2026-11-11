package com.radwanalbunni.automation.data.models.domain;

import com.radwanalbunni.automation.data.ReferenceDataStore;
import com.radwanalbunni.automation.data.ResolvableTestData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Data for one independent cart scenario.
 */
public class CartDataModel extends TestDataModel implements ResolvableTestData {

    private String expectedResult;
    // Each quantity belongs to the product at the same array index.
    private int[] quantities;

    private String[] productIds;
    private String[] productsToRemoveIds;

    @JsonIgnore
    private List<ProductDataModel> products;
    @JsonIgnore
    private List<ProductDataModel> productsToRemove;

    public CartDataModel() {
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public void setQuantities(int[] quantities) {
        this.quantities = quantities;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public String[] getProductsToRemoveIds() {
        return productsToRemoveIds;
    }

    public void setProductsToRemoveIds(String[] productsToRemoveIds) {
        this.productsToRemoveIds = productsToRemoveIds;
    }

    @JsonIgnore
    public List<ProductDataModel> getProducts() {
        return products;
    }

    @JsonIgnore
    public void setProducts(List<ProductDataModel> products) {
        this.products = products;
    }

    @JsonIgnore
    public List<ProductDataModel> getProductsToRemove() {
        return productsToRemove;
    }

    @JsonIgnore
    public void setProductsToRemove(List<ProductDataModel> productsToRemove) {
        this.productsToRemove = productsToRemove;
    }

    @Override
    public void resolveReferences(ReferenceDataStore references) {
        if (productIds != null) {
            products = references.products(productIds);
        }
        if (productsToRemoveIds != null) {
            productsToRemove = references.products(productsToRemoveIds);
        }
    }
}
