package com.radwanalbunni.tests.ui;

import com.radwanalbunni.automation.assertions.UiAssertions;
import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.base.UiBaseTest;
import com.radwanalbunni.automation.ui.utils.DownloadUtils;
import com.radwanalbunni.automation.data.models.domain.CheckoutDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import com.radwanalbunni.automation.data.GenericDataProvider;
import com.radwanalbunni.automation.data.factory.TestUserFactory;
import com.radwanalbunni.automation.ui.flows.CheckoutFlow;
import com.radwanalbunni.automation.ui.pages.CheckoutPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Epic("Checkout")
@Feature("Checkout Process")
public class CheckoutUiTest extends UiBaseTest {

    private final TestUserFactory testUserFactory = new TestUserFactory();
    private UserDataModel testUser;

    /**
     * Start every checkout scenario with an empty download folder.
     */
    @BeforeMethod(alwaysRun = true)
    public void clearPreviousDownloads() {
        DownloadUtils.clearDownloads();
    }

    /**
     * Creates an account only for scenarios that need checkout state.
     */
    @BeforeMethod(alwaysRun = true)
    public void createIsolatedTestUser(ITestResult result) {
        if (Arrays.asList(result.getMethod().getGroups()).contains("requiresAccount")) {
            testUser = testUserFactory.createUser();
        }
    }

    /**
     * Removes the isolated account after each checkout scenario.
     */
    @AfterMethod(alwaysRun = true)
    public void deleteIsolatedTestUser() {
        if (testUser != null) {
            testUserFactory.deleteUser(testUser);
            testUser = null;
        }
    }

    @Story("Place an order")
    @Severity(SeverityLevel.CRITICAL)
    @TestDataType("validCheckout")
    @TestDataFile("ui/checkout")
    @Test(groups = {"smoke", "requiresAccount"}, dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testOrderPlacedSuccessfully(CheckoutDataModel data) {
        CheckoutPage checkoutPage = new CheckoutFlow().placeOrder(testUser, data);

        UiAssertions.assertTextEquals(
                checkoutPage.getOrderSuccessMessage(),
                data.getExpectedResult(),
                "Order confirmation message"
        );
    }

    @Story("Download invoice after placing an order")
    @TestDataType("validCheckout")
    @TestDataFile("ui/checkout")
    @Test(groups = "requiresAccount", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testInvoiceDownloadAfterOrder(CheckoutDataModel data) {
        CheckoutPage checkoutPage = new CheckoutFlow().placeOrder(testUser, data);
        checkoutPage.clickDownloadInvoiceButton();
        Path invoiceFile = DownloadUtils.waitForTextFile();

        UiAssertions.assertTrue(
                Files.isRegularFile(invoiceFile),
                "Downloaded invoice file should exist"
        );

        UiAssertions.assertTextContains(
                DownloadUtils.readTextFile(invoiceFile),
                data.getExpectedInvoiceContent(),
                "invoice content"
        );
    }

    @Story("Validate required payment details")
    @TestDataType("validCheckout")
    @TestDataFile("ui/checkout")
    @Test(groups = "requiresAccount", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testPaymentRequiresDetails(CheckoutDataModel data) {
        CheckoutPage checkoutPage = new CheckoutFlow().reachPaymentPage(testUser, data);
        checkoutPage.submitEmptyPaymentDetails();

        UiAssertions.assertTextEquals(
                checkoutPage.getPaymentNameValidationMessage(),
                data.getExpectedValidationMessage(),
                "Payment name validation message"
        );
    }

    @Story("Validate required card number")
    @TestDataType("missingCardNumber")
    @TestDataFile("ui/checkout")
    @Test(groups = "requiresAccount", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testPaymentRequiresCardNumber(CheckoutDataModel data) {
        CheckoutPage checkoutPage = new CheckoutFlow().reachPaymentPage(testUser, data);
        checkoutPage.fillPaymentDetails(data.getPaymentCard());

        UiAssertions.assertTextEquals(
                checkoutPage.getPaymentCardNumberValidationMessage(),
                data.getExpectedValidationMessage(),
                "Card number validation message"
        );
    }

    @Story("Validate required card CVV")
    @TestDataType("missingCvv")
    @TestDataFile("ui/checkout")
    @Test(groups = "requiresAccount", dataProvider = "data", dataProviderClass = GenericDataProvider.class)
    public void testPaymentRequiresCvv(CheckoutDataModel data) {
        CheckoutPage checkoutPage = new CheckoutFlow().reachPaymentPage(testUser, data);
        checkoutPage.fillPaymentDetails(data.getPaymentCard());

        UiAssertions.assertTextEquals(
                checkoutPage.getPaymentCvvValidationMessage(),
                data.getExpectedValidationMessage(),
                "CVV validation message"
        );
    }

}
