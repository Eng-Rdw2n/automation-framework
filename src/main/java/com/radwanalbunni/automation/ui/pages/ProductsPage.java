package com.radwanalbunni.automation.ui.pages;

import com.radwanalbunni.automation.data.models.domain.ProductDataModel;
import com.radwanalbunni.automation.data.models.domain.ReviewDataModel;
import com.radwanalbunni.automation.data.models.domain.SearchDataModel;
import com.radwanalbunni.automation.ui.components.Header;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class ProductsPage extends BasePage {

    // Search
    private static final By SEARCH_FIELD = By.id("search_product");
    private static final By SEARCH_BUTTON = By.id("submit_search");

    // Product
    private static final By VIEW_PRODUCT_BUTTON = By.cssSelector("a[href*='product_details']");

    // Product Details
    private static final By QUANTITY_FIELD = By.id("quantity");
    private static final By INNER_ADD_TO_CART_BUTTON = By.cssSelector("button.cart");

    // Review Form
    private static final By REVIEW_NAME_FIELD = By.id("name");
    private static final By REVIEW_EMAIL_FIELD = By.id("email");
    private static final By REVIEW_TEXTAREA = By.id("review");
    private static final By REVIEW_SUBMIT_BUTTON = By.id("button-review");
    private static final By REVIEW_SUBMISSION_SUCCESS_MESSAGE = By.cssSelector("#review-section .alert-success");

    // Continue Shopping Button
    private static final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("button.close-modal");

    @Step("Search for configured product")
    public void searchForProduct(SearchDataModel data) {
        if (data.getProduct() != null) {
            searchForProduct(data.getProduct());
            return;
        }
        searchProduct(data.getSearchKeyword());
    }

    @Step("Search for expected product")
    public void searchForProduct(ProductDataModel product) {
        searchProduct(product.getSearchKeyword());
    }

    @Step("Select the category of the expected product")
    public void selectCategory(ProductDataModel product) {
        selectCategory(product.getCategory(), product.getSubCategory());
    }

    private void selectCategory(String category, String subCategory) {
        By categoryLocator = By.xpath("//div[@class='panel-heading']//a[contains(normalize-space(),'" + category + "')]");
        click(categoryLocator);
        By subCategoryLocator = By.xpath("//div[@id='" + category + "']//a[normalize-space()='" + subCategory + "']");
        click(subCategoryLocator);
    }

    @Step("Select the brand of the expected product")
    public void selectBrand(ProductDataModel product) {
        selectBrand(product.getBrand());
    }

    private void selectBrand(String brand) {
        By brandLocator = By.xpath("//div[contains(@class,'brands_products')]//a[contains(normalize-space(),'" + brand + "')]");
        click(brandLocator);
    }

    @Step("Get displayed product name")
    public String getDisplayedProductName(ProductDataModel product) {
        return getText(productNameLocator(product));
    }

    @Step("Verify no products are displayed in search results")
    public boolean hasNoSearchResults() {
        return driver.findElements(By.cssSelector(".features_items .product-image-wrapper")).isEmpty();
    }

    @Step("Verify product catalog is displayed")
    public boolean isProductCatalogDisplayed() {
        return !driver.findElements(VIEW_PRODUCT_BUTTON).isEmpty();
    }

    @Step("Add products to cart with requested quantities")
    public void addToCartWithQuantities(List<ProductDataModel> products, int[] quantities) {
        if (products.size() != quantities.length) {
            throw new IllegalArgumentException(
                    "Products and quantities must have the same number of entries."
            );
        }

        for (int index = 0; index < products.size(); index++) {
            ProductDataModel product = products.get(index);
            int quantity = quantities[index];

            if (quantity < 1) {
                throw new IllegalArgumentException("Product quantity must be at least 1.");
            }

            searchProduct(product.getSearchKeyword());
            click(VIEW_PRODUCT_BUTTON);
            type(QUANTITY_FIELD, String.valueOf(quantity));
            click(INNER_ADD_TO_CART_BUTTON);
            continueShopping();

            // Adding by quantity opens a product-details page. Return to the
            // catalog before searching for the next product in this scenario.
            if (index < products.size() - 1) {
                new Header().navigateToProducts();
            }
        }
    }

    @Step("Continue Shopping after adding product to cart")
    public void continueShopping() {
        click(CONTINUE_SHOPPING_BUTTON);
    }

    @Step("Submit review for product")
    public void submitReview(ReviewDataModel data) {
        openProductDetails(data.getProduct());
        type(REVIEW_NAME_FIELD, data.getUser().getName());
        type(REVIEW_EMAIL_FIELD, data.getUser().getEmail());
        type(REVIEW_TEXTAREA, data.getReview());
        click(REVIEW_SUBMIT_BUTTON);
    }

    @Step("Get product review result message")
    public String getReviewSubmissionSuccessMessage() {
        return getText(REVIEW_SUBMISSION_SUCCESS_MESSAGE);
    }

    public void openProductDetails(ProductDataModel product) {
        searchProduct(product.getSearchKeyword());
        click(VIEW_PRODUCT_BUTTON);
    }

    public void submitEmptyReview() {
        click(REVIEW_SUBMIT_BUTTON);
    }

    public String getReviewNameValidationMessage() {
        return getNativeValidationMessage(REVIEW_NAME_FIELD);
    }

    public String getReviewEmailValidationMessage() {
        return getNativeValidationMessage(REVIEW_EMAIL_FIELD);
    }

    public String getReviewTextValidationMessage() {
        return getNativeValidationMessage(REVIEW_TEXTAREA);
    }

    public boolean isReviewEmailValid() {
        return isFieldValid(REVIEW_EMAIL_FIELD);
    }


    // Private helper method to search for a product
    private void searchProduct(String productName) {
        type(SEARCH_FIELD, productName);
        click(SEARCH_BUTTON);
    }

    private By productNameLocator(ProductDataModel product) {
        return By.xpath(
                "//div[contains(@class,'productinfo')]//p[normalize-space()='" + product.getName() + "']"
        );
    }
}
