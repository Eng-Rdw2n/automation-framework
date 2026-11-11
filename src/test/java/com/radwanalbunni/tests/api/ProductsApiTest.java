package com.radwanalbunni.tests.api;

import com.radwanalbunni.automation.api.clients.ProductClient;
import com.radwanalbunni.automation.assertions.ApiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.data.models.domain.ApiScenarioDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.models.response.ProductsResponse;
import com.radwanalbunni.automation.data.GenericDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Catalog API")
@Feature("Products API")
public class ProductsApiTest {

    private final ProductClient productClient = new ProductClient();

    @Story("Get all products")
    @TestDataFile("api/products")
    @TestDataType("getAllProducts")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testGetAllProducts(ApiScenarioDataModel data) {
        ProductsResponse response = productClient.getAllProducts();

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertListNotEmpty(response.getProducts(), "products");
    }

    @Story("Reject unsupported products method")
    @TestDataFile("api/products")
    @TestDataType("unsupportedPost")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testPostToProductsIsRejected(ApiScenarioDataModel data) {
        ApiMessageResponse response = productClient.postToProducts();

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertMessage(response.getMessage(), data.getExpectedResult());
    }
}
