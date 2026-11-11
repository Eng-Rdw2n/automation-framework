package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.data.models.domain.ReviewDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.ProductsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Epic("Products")
@Feature("Product Reviews")
public class ReviewUiTest extends UiBaseTest {

    @Story("Submit a product review")
    @Description("Verify a user can submit a valid review for a product")
    @TestDataFile("ui/review")
    @TestDataType("validReview")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testSubmitProductReview(ReviewDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.submitReview(data);

        UiAssertions.assertTextEquals(
                productsPage.getReviewSubmissionSuccessMessage(),
                data.getExpectedResult(),
                "Product review confirmation message"
        );
    }

    @Story("Validate required review fields")
    @Description("Verify the review form does not submit when required fields are empty")
    @TestDataFile("ui/review")
    @TestDataType("validReview")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testReviewRequiresDetails(ReviewDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.openProductDetails(data.getProduct());
        productsPage.submitEmptyReview();

        UiAssertions.assertTextEquals(
                productsPage.getReviewNameValidationMessage(),
                data.getExpectedValidationMessage(),
                "Required review name validation message"
        );
    }

    @Story("Reject invalid review email")
    @Description("Verify the review form rejects an invalid email format")
    @TestDataFile("ui/review")
    @TestDataType("invalidEmail")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testReviewRejectsInvalidEmail(ReviewDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.submitReview(data);

        UiAssertions.assertFalse(
                productsPage.isReviewEmailValid(),
                "Invalid review email should be rejected by browser validation"
        );
    }

    @Story("Validate required review email")
    @Description("Verify the review form does not submit when the email is empty")
    @TestDataFile("ui/review")
    @TestDataType("missingEmail")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testReviewRequiresEmail(ReviewDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.submitReview(data);

        UiAssertions.assertTextEquals(
                productsPage.getReviewEmailValidationMessage(),
                data.getExpectedValidationMessage(),
                "Required review email validation message"
        );
    }

    @Story("Validate required review text")
    @Description("Verify the review form does not submit when the review text is empty")
    @TestDataFile("ui/review")
    @TestDataType("missingReviewText")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testReviewRequiresText(ReviewDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();

        header.navigateToProducts();
        productsPage.submitReview(data);

        UiAssertions.assertTextEquals(
                productsPage.getReviewTextValidationMessage(),
                data.getExpectedValidationMessage(),
                "Required review text validation message"
        );
    }
}
