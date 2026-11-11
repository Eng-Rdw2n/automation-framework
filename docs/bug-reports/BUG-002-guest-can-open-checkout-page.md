# BUG-002 — Guest Can Open the Checkout Page Directly

| Field | Details |
|---|---|
| Environment | QA — Chrome — 18 October 2026 |
| Severity | Low |
| Priority | Low |
| Preconditions | Open a new browser session. Do not log in. |
| Status | Direct page access reproduced; full checkout outcome needs documented evidence |

## Steps to reproduce

1. Open `https://automationexercise.com/checkout` directly.
2. Do not log in.

## Actual result

The website displays the Checkout page with address sections, an order-comment field, and a `Place Order` link. The cart was empty and no order was submitted during the recorded direct-URL check. A later guest checkout flow was reported, but its steps and final order result have not yet been recorded here.

## Expected result

An unauthenticated visitor must not be able to place an order. Whether viewing an empty Checkout page should also require login needs a separate product requirement.

## Evidence

Direct URL tested in a new guest browser session on 18 October 2026. To confirm an order-placement defect, record the guest's cart contents, navigation path, payment submission, and final page without signing in.
