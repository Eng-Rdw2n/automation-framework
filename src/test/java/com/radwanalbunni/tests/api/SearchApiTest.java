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
@Feature("Product Search API")
public class SearchApiTest {

    private final ProductClient productClient = new ProductClient();

    @Story("Search products")
    @TestDataFile("api/products")
    @TestDataType("validSearch")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSearchProducts(ApiScenarioDataModel data) {
        ProductsResponse response = productClient.searchProducts(data.getSearchProduct());

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertListNotEmpty(response.getProducts(), "products");
    }

    @Story("Reject search without a search parameter")
    @TestDataFile("api/products")
    @TestDataType("missingSearchParameter")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSearchWithoutParameterIsRejected(ApiScenarioDataModel data) {
        ApiMessageResponse response = productClient.searchWithoutParameter();

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertMessage(response.getMessage(), data.getExpectedResult());
    }

    @Story("Search with no matching products")
    @TestDataFile("api/products")
    @TestDataType("searchNoResults")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSearchWithNoResults(ApiScenarioDataModel data) {
        ProductsResponse response = productClient.searchProducts(data.getSearchProduct());

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertListEmpty(response.getProducts(), "products");
    }
}
