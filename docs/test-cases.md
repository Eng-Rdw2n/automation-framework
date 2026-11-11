# Smoke Suite Test Case Samples

These are sample manual test case designs for the smoke suite. They cover selected critical customer journeys and supporting APIs; they are not a complete test-case catalogue or a record of manual execution results.

The automated smoke tests use Allure severity to reflect the priorities below: **Critical** test cases use `CRITICAL`, and **High** test cases use `NORMAL` because Allure has no `HIGH` severity level.

| ID | Requirement or endpoint | Scenario | Priority |
|---|---|---|---|
| TC-001 | REQ-UI-001 | Log in successfully through the UI | Critical |
| TC-002 | REQ-UI-002 | Log out successfully through the UI | High |
| TC-003 | `POST /api/verifyLogin` | Verify valid login through the API | Critical |
| TC-004 | REQ-UI-003 | Register a new account through the UI | Critical |
| TC-005 | `POST /api/createAccount` | Create a new account through the API | Critical |
| TC-006 | REQ-UI-006 | Add a product to the cart through the UI | Critical |
| TC-007 | REQ-UI-007 | Complete checkout through the UI | Critical |
| TC-008 | REQ-UI-004 | Delete an account through the UI | High |
| TC-009 | `DELETE /api/deleteAccount` | Delete an account through the API | High |

---

## TC-001 — Valid UI Login

| Field | Value |
|---|---|
| Requirement | REQ-UI-001 |
| Preconditions | A valid customer account exists. |

### Steps

1. Open the home page.
2. Navigate to **Signup / Login**.
3. Enter valid email and password credentials.
4. Submit the login form.

### Expected Result

- The user is logged in successfully.
- The header displays the logged-in user name.

---

## TC-002 — Logout

| Field | Value |
|---|---|
| Requirement | REQ-UI-002 |
| Preconditions | A valid customer account exists. |

### Steps

1. Open the home page.
2. Navigate to **Signup / Login**.
3. Log in with valid credentials.
4. Select **Logout**.

### Expected Result

- The user is logged out successfully.
- The system returns the user to the login page.

---

## TC-003 — Valid API Login

| Field | Value |
|---|---|
| Endpoint | `POST /api/verifyLogin` |
| Preconditions | A valid customer account exists. |

### Steps

1. Send `POST /api/verifyLogin`.
2. Include valid `email` and `password` form parameters.

### Expected Result

- HTTP response status is `200`.
- Response body contains `responseCode: 200`.
- Response body message is `User exists!`.

---

## TC-004 — Valid UI Registration

| Field | Value |
|---|---|
| Requirement | REQ-UI-003 |
| Preconditions | A unique email address is available. |

### Steps

1. Open **Signup / Login**.
2. Enter a name and unique email address.
3. Complete all mandatory account and address details.
4. Submit the registration form.

### Expected Result

- The account is created successfully.
- The account-created confirmation is shown.

---

## TC-005 — Create Account Through API

| Field | Value |
|---|---|
| Endpoint | `POST /api/createAccount` |
| Preconditions | A unique email address is available. |

### Steps

1. Send `POST /api/createAccount`.
2. Include all required account profile form parameters.

### Expected Result

- HTTP response status is `200`.
- Response body contains `responseCode: 201`.
- Response body message is `User created!`.

---

## TC-006 — Add Product to Cart

| Field | Value |
|---|---|
| Requirement | REQ-UI-006 |
| Preconditions | A valid customer account is available. |

### Steps

1. Log in with a valid customer account.
2. Navigate to **Products**.
3. Add the configured products with their requested quantities.
4. Open the cart.

### Expected Result

- Each configured product appears in the cart.
- Each product quantity matches the configured quantity.

---

## TC-007 — Checkout

| Field | Value |
|---|---|
| Requirement | REQ-UI-007 |
| Preconditions | A valid customer account is available. |

### Steps

1. Log in with a valid customer account.
2. Add a product to the cart.
3. Proceed to checkout.
4. Enter valid payment-card details.
5. Confirm the order.

### Expected Result

- The order is placed successfully.
- The order confirmation message is displayed.

---

## TC-008 — Delete Account Through the UI

| Field | Value |
|---|---|
| Requirement | REQ-UI-004 |
| Preconditions | A valid customer account exists and the user is logged in. |

### Steps

1. Select **Delete Account**.
2. Verify the account-deleted confirmation page.

### Expected Result

- The account is deleted successfully.
- The account-deleted confirmation is displayed.

---

## TC-009 — Delete Account Through the API

| Field | Value |
|---|---|
| Endpoint | `DELETE /api/deleteAccount` |
| Preconditions | A unique test account is created through `POST /api/createAccount`. |

### Steps

1. Send `DELETE /api/deleteAccount`.
2. Include the test account's `email` and `password` form parameters.

### Expected Result

- HTTP response status is `200`.
- Response body contains `responseCode: 200`.
- Response body message is `Account deleted!`.
