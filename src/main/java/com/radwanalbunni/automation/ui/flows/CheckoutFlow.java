package com.radwanalbunni.automation.ui.flows;

import com.radwanalbunni.automation.data.models.domain.CheckoutDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import com.radwanalbunni.automation.ui.components.Header;
import com.radwanalbunni.automation.ui.pages.AccountPage;
import com.radwanalbunni.automation.ui.pages.CartPage;
import com.radwanalbunni.automation.ui.pages.CheckoutPage;
import com.radwanalbunni.automation.ui.pages.ProductsPage;
import io.qameta.allure.Step;

/**
 * Reusable journey that creates an order across the Login, Products, Cart, and Checkout pages.
 */
public class CheckoutFlow {

    @Step("Complete checkout journey")
    public CheckoutPage placeOrder(UserDataModel user, CheckoutDataModel data) {
        CheckoutPage checkoutPage = reachPaymentPage(user, data);
        checkoutPage.fillPaymentDetails(data.getPaymentCard());
        return checkoutPage;
    }

    /**
     * Completes the journey up to the payment page without submitting payment details.
     */
    public CheckoutPage reachPaymentPage(UserDataModel user, CheckoutDataModel data) {
        Header header = new Header();
        AccountPage accountPage = new AccountPage();
        ProductsPage productsPage = new ProductsPage();
        CartPage cartPage = new CartPage();
        CheckoutPage checkoutPage = new CheckoutPage();

        header.navigateToLoginSignup();
        accountPage.login(user);
        header.navigateToProducts();
        productsPage.addToCartWithQuantities(data.getProducts(), data.getQuantities());
        header.navigateToCart();
        cartPage.proceedToCheckout();
        checkoutPage.addCommentOnCheckout(data.getComment());
        checkoutPage.clickPlaceOrderButton();
        return checkoutPage;
    }
}
