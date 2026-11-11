package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.data.models.domain.SearchDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.ProductsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("Product Discovery")
public class SearchUiTest extends UiBaseTest {

    @Story("Search by keyword")
    @Description("Verify a product can be found through the search bar")
    @TestDataFile("ui/search")
    @TestDataType("searchByKeyword")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testFindProductBySearchBar(SearchDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.searchForProduct(data.getProduct());

        UiAssertions.assertTextEquals(
                productsPage.getDisplayedProductName(data.getProduct()),
                data.getExpectedResult(),
                "Search result product name"
        );
    }

    @Story("Browse by category")
    @Description("Verify a product can be found through its category and subcategory")
    @TestDataFile("ui/search")
    @TestDataType("searchByCategory")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testFindProductByCategory(SearchDataModel data) {
        ProductsPage productsPage = new ProductsPage();

        productsPage.selectCategory(data.getProduct());

        UiAssertions.assertTextEquals(
                productsPage.getDisplayedProductName(data.getProduct()),
                data.getExpectedResult(),
                "Category result product name"
        );
    }

    @Story("Browse by brand")
    @Description("Verify a product can be found through its brand")
    @TestDataFile("ui/search")
    @TestDataType("searchByBrand")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testFindProductByBrand(SearchDataModel data) {
        ProductsPage productsPage = new ProductsPage();

        productsPage.selectBrand(data.getProduct());

        UiAssertions.assertTextEquals(
                productsPage.getDisplayedProductName(data.getProduct()),
                data.getExpectedResult(),
                "Brand result product name"
        );
    }

    @Story("Search with no matching products")
    @Description("Verify a search with no matching keyword displays no product cards")
    @TestDataFile("ui/search")
    @TestDataType("searchNoResults")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSearchWithUnknownKeyword(SearchDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.searchForProduct(data);

        UiAssertions.assertTrue(
                productsPage.hasNoSearchResults(),
                "Unknown search term should return no products"
        );
    }
}
