package com.radwanalbunni.tests.api;

import com.radwanalbunni.automation.api.clients.BrandClient;
import com.radwanalbunni.automation.assertions.ApiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.data.models.domain.ApiScenarioDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.models.response.BrandsResponse;
import com.radwanalbunni.automation.data.GenericDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Catalog API")
@Feature("Brands API")
public class BrandsApiTest {

    private final BrandClient brandClient = new BrandClient();

    @Story("Get all brands")
    @TestDataFile("api/brands")
    @TestDataType("getAllBrands")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testGetAllBrands(ApiScenarioDataModel data) {
        BrandsResponse response = brandClient.getAllBrands();

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertListNotEmpty(response.getBrands(), "brands");
    }

    @Story("Reject unsupported brands method")
    @TestDataFile("api/brands")
    @TestDataType("unsupportedPut")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testPutToBrandsIsRejected(ApiScenarioDataModel data) {
        ApiMessageResponse response = brandClient.putToBrands();

        ApiAssertions.assertHttpStatus(response.getHttpStatus(), data.getExpectedHttpStatus());
        ApiAssertions.assertBodyResponseCode(response.getResponseCode(), data.getExpectedResponseCode());
        ApiAssertions.assertMessage(response.getMessage(), data.getExpectedResult());
    }
}
