# Requirements Traceability Matrix (RTM)

The UI features, API operations, and crucial rules come from [system analysis](system-analysis.md). This matrix links each one to its automated test methods. The Coverage column describes automation coverage; it does not report manual test execution. A gap means no automated test checks that rule.

| ID | Feature | Automated Test | Coverage |
|---|---|---|---|
| REQ-UI-001 | Login | `LoginUiTest.testValidLogin` | Covered |
| REQ-UI-002 | Logout | `LoginUiTest.testLogoutAfterValidLogin` | Covered |
| REQ-UI-003 | Registration | `RegistrationUiTest.testUserRegistration`, `RegistrationUiTest.testSignupRequiresName`, `RegistrationUiTest.testSignupRequiresEmail` | Covered |
| REQ-UI-004 | Delete account | `RegistrationUiTest.testDeleteNewlyRegisteredAccount` | Covered |
| REQ-UI-005 | Product discovery | `SearchUiTest.testFindProductBySearchBar`, `SearchUiTest.testFindProductByCategory`, `SearchUiTest.testFindProductByBrand`, `SearchUiTest.testSearchWithUnknownKeyword` | Covered |
| REQ-UI-006 | Cart Management | `CartUiTest.testAddToCart`, `CartUiTest.testRemoveSelectedProductsFromCart` | Covered |
| REQ-UI-007 | Checkout | `CheckoutUiTest.testOrderPlacedSuccessfully` | Covered |
| REQ-UI-008 | Invoice | `CheckoutUiTest.testInvoiceDownloadAfterOrder` | Covered |
| REQ-UI-009 | Contact support | `ContactUsUiTest.testContactUsForm`, `ContactUsUiTest.testContactFormWithoutAttachment` | Covered |
| REQ-UI-010 | Product review | `ReviewUiTest.testSubmitProductReview`, `ReviewUiTest.testReviewRequiresEmail`, `ReviewUiTest.testReviewRequiresText` | Covered |
| REQ-UI-011 | Newsletter subscription | `SubscriptionUiTest.testNewsletterSubscription` | Covered |
| API-001 | Get All Products | `ProductsApiTest.testGetAllProducts` | Covered |
| API-002 | Get All Brands | `BrandsApiTest.testGetAllBrands` | Covered |
| API-003 | Search Products | `SearchApiTest.testSearchProducts`, `SearchApiTest.testSearchWithNoResults` | Covered |
| API-004 | Verify Login | `LoginApiTest.testValidLogin` | Covered |
| API-005 | Create Account | `RegistrationApiTest.testCreateAccount` | Covered |
| API-006 | Delete Account | `AccountApiTest.testDeleteAccount` | Covered |
| API-007 | Update Account | `AccountApiTest.testUpdateAccount` | Covered |
| API-008 | Get User Details | `AccountApiTest.testGetUserDetail` | Covered |
| RULE-001 | Login requires valid credentials | `LoginUiTest.testInvalidLogin`, `LoginUiTest.testLoginRequiresEmailAndPassword`, `LoginUiTest.testLoginWithLongEmailIsRejected` | Covered |
| RULE-002 | Registration email must be unique | `RegistrationUiTest.testRegistrationWithExistingEmail` | Covered |
| RULE-003 | Account deletion requires authentication | — | Gap; [OBS-001](bug-reports/OBS-001-guest-sees-account-deleted-message.md) shows a misleading message, not unauthorized deletion |
| RULE-004 | Cart Management retains selected products and quantities | `CartUiTest.testAddToCart`, `CartUiTest.testRemoveSelectedProductsFromCart` | Covered |
| RULE-005 | Checkout requires authentication | `CartUiTest.testCheckoutRequiresLogin` | Partial: cart path tested; direct URL behavior reported in [BUG-002](bug-reports/BUG-002-guest-can-open-checkout-page.md) |
| RULE-006 | Checkout requires payment details | `CheckoutUiTest.testPaymentRequiresDetails`, `CheckoutUiTest.testPaymentRequiresCardNumber`, `CheckoutUiTest.testPaymentRequiresCvv` | Covered |
| RULE-007 | Invoice is available after a successful order | `CheckoutUiTest.testInvoiceDownloadAfterOrder` | Covered |
| RULE-008 | Contact support requires a valid email | `ContactUsUiTest.testContactFormRequiresEmail`, `ContactUsUiTest.testContactFormRejectsInvalidEmail` | Covered |
| RULE-009 | Product review requires valid mandatory details | `ReviewUiTest.testReviewRequiresDetails`, `ReviewUiTest.testReviewRequiresEmail`, `ReviewUiTest.testReviewRequiresText`, `ReviewUiTest.testReviewRejectsInvalidEmail` | Covered |
| RULE-010 | Newsletter subscription requires a valid email | `SubscriptionUiTest.testSubscriptionRequiresEmail`, `SubscriptionUiTest.testSubscriptionRejectsInvalidEmail` | Covered |
| RULE-011 | Product search requires `search_product` | `SearchApiTest.testSearchWithoutParameterIsRejected` | Covered |
| RULE-012 | Login requires email and password | `LoginApiTest.testLoginWithoutEmail`, `LoginApiTest.testLoginWithoutPassword` | Covered |
| RULE-013 | Login credentials must belong to an account | `LoginApiTest.testInvalidLogin` | Covered |
| RULE-014 | Account creation uses profile fields and unique email | `RegistrationApiTest.testCreateAccount`, `RegistrationApiTest.testRegistrationWithDuplicateEmail` | Covered |
| RULE-015 | Account deletion requires email and password | `AccountApiTest.testDeleteAccount`, `AccountApiTest.testDeleteAccountWithInvalidPassword` | Covered |
| RULE-016 | Unsupported HTTP methods are rejected | `ProductsApiTest.testPostToProductsIsRejected`, `BrandsApiTest.testPutToBrandsIsRejected`, `LoginApiTest.testDeleteVerifyLoginIsRejected` | Covered |
