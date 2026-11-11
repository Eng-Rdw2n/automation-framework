package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.data.models.domain.CartDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.data.factory.TestUserFactory;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.AccountPage;
import com.radwanalbunni.automation.ui.pages.CartPage;
import com.radwanalbunni.automation.ui.pages.ProductsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;

@Epic("Cart")
@Feature("Shopping Cart Functionality")
public class CartUiTest extends UiBaseTest {

    private final TestUserFactory testUserFactory = new TestUserFactory();
    private UserDataModel testUser;

    /**
     * Creates an account only for scenarios that need authenticated cart state.
     */
    @BeforeMethod(alwaysRun = true)
    public void createIsolatedTestUser(ITestResult result) {
        if (Arrays.asList(result.getMethod().getGroups()).contains("requiresAccount")) {
            testUser = testUserFactory.createUser();
        }
    }

    /**
     * Keeps the public sandbox clean even when the test fails.
     */
    @AfterMethod(alwaysRun = true)
    public void deleteIsolatedTestUser() {
        if (testUser != null) {
            testUserFactory.deleteUser(testUser);
            testUser = null;
        }
    }

    @Story("Add products with requested quantities")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify a logged-in user can add products to the cart with the requested quantities")
    @TestDataFile("ui/cart")
    @TestDataType("addAndRemoveProducts")
    @Test(groups = {"smoke", "requiresAccount"}, dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testAddToCart(CartDataModel data) {

        Header header = new Header();
        header.navigateToLoginSignup();
        AccountPage accountPage = new AccountPage();
        accountPage.login(testUser);
        header.navigateToProducts();
        ProductsPage productsPage = new ProductsPage();
        productsPage.addToCartWithQuantities(data.getProducts(), data.getQuantities());
        header.navigateToCart();
        CartPage cartPage = new CartPage();

        UiAssertions.assertTrue(
                cartPage.areProductQuantitiesCorrect(data.getProducts(), data.getQuantities()),
                "Cart quantities should match the scenario data"
        );
    }

    @Story("Remove selected products")
    @Description("Verify a logged-in user can remove selected products from the cart")
    @TestDataFile("ui/cart")
    @TestDataType("addAndRemoveProducts")
    @Test(groups = "requiresAccount", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testRemoveSelectedProductsFromCart(CartDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();
        ProductsPage productsPage = new ProductsPage();
        CartPage cartPage = new CartPage();

        header.navigateToLoginSignup();
        accountPage.login(testUser);
        header.navigateToProducts();
        productsPage.addToCartWithQuantities(data.getProducts(), data.getQuantities());
        header.navigateToCart();
        cartPage.deleteProducts(data.getProductsToRemove());

        UiAssertions.assertTrue(
                cartPage.areProductsRemoved(data.getProductsToRemove()),
                "Selected products should be removed from the cart"
        );
    }

    @Story("Checkout requires login")
    @Description("Verify a visitor is asked to log in before checkout")
    @TestDataFile("ui/cart")
    @TestDataType("checkoutRequiresLogin")
    @Test(dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testCheckoutRequiresLogin(CartDataModel data) {
        Header header = new Header();
        ProductsPage productsPage = new ProductsPage();
        CartPage cartPage = new CartPage();

        header.navigateToProducts();
        productsPage.addToCartWithQuantities(data.getProducts(), data.getQuantities());
        header.navigateToCart();
        cartPage.proceedToCheckout();

        UiAssertions.assertTextEquals(
                cartPage.getLoginRequiredMessage(),
                data.getExpectedResult(),
                "Guest checkout message"
        );
    }


}
