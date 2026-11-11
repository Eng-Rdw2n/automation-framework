package com.radwanalbunni.automation.ui.pages;

import com.radwanalbunni.automation.data.models.domain.ProductDataModel;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.List;

public class CartPage extends BasePage {

    // Cart Page
    private static final By PROCEED_TO_CHECKOUT_BUTTON = By.cssSelector("a.check_out");

    // Login Required Modal
    private static final By LOGIN_MODAL_MESSAGE = By.xpath("//div[@class='modal-body']//p[contains(text(),'Register / Login account')]");


    @Step("Proceed to Checkout")
    public void proceedToCheckout() {
        click(PROCEED_TO_CHECKOUT_BUTTON);
    }

    @Step("Get login-required checkout message")
    public String getLoginRequiredMessage() {
        return getText(LOGIN_MODAL_MESSAGE);
    }

    @Step("Delete selected products from cart")
    public void deleteProducts(List<ProductDataModel> products) {
        for (ProductDataModel product : products) {
            By deleteButton = By.xpath(
                    "//a[normalize-space()='" + product.getName() + "']" +
                            "/ancestor::tr//a[contains(@class,'cart_quantity_delete')]"
            );
            click(deleteButton);
        }
    }

    @Step("Verify selected products are removed from cart")
    public boolean areProductsRemoved(List<ProductDataModel> products) {
        for (ProductDataModel product : products) {
            By productLink = By.xpath("//a[normalize-space()='" + product.getName() + "']");
            if (!waitUtils.waitForInvisibility(productLink)) {
                return false;
            }
        }
        return true;
    }

    @Step("Verify product quantities in cart")
    public boolean areProductQuantitiesCorrect(List<ProductDataModel> products, int[] quantities) {
        if (products.size() != quantities.length) {
            return false;
        }
        for (int index = 0; index < products.size(); index++) {
            ProductDataModel product = products.get(index);
            By quantity = By.xpath(
                    "//a[normalize-space()='" + product.getName() + "']" +
                            "/ancestor::tr//td[contains(@class,'cart_quantity')]//button"
            );

            if (!String.valueOf(quantities[index]).equals(getText(quantity).trim())) {
                return false;
            }
        }
        return true;
    }

}
