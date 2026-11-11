# Automation Test Strategy

## Project Objective

> **Analyse the system, define its features, document its APIs, and identify crucial rules. Then use automated UI and API tests to verify that features work as expected and crucial rules are not broken.**

## 1. Purpose

This project is a portfolio test automation framework for the public
[`automationexercise.com`](https://automationexercise.com/) sandbox. Its purpose is to demonstrate a practical approach to UI and API quality assurance using Java, Selenium, TestNG, REST Assured, and Allure.

The framework focuses on reliable, readable, data-driven automated tests rather than maximizing the number of tests.
This strategy covers automated UI and API testing. The separate sample manual test cases document test design, not manual execution results.

## 2. Scope

### In scope

- UI journeys: registration, login, delete account, logout, product search, cart, checkout, contact form, product review, and newsletter subscription.
- API endpoints: products, brands, product search, login verification, account creation, account retrieval, account update, and account deletion.
- Positive journeys and selected negative validations.
- Chrome and Firefox local execution.
- QA environment configuration and data.

### Out of scope

- Performance, load, accessibility, security, and mobile-device testing.
- Real payment processing. The target application is a public training sandbox.
- Production test execution, multi-environment execution, or use of real customer data.

## 3. Test Approach

| Test level | Approach | Main tools |
|---|---|---|
| UI | Page Objects, reusable components, explicit waits, and shared UI assertions backed by TestNG | Selenium, TestNG |
| API | REST Assured clients, typed response models, and shared API assertions | REST Assured, TestNG |
| Data | JSON scenario data is supplied to tests through a generic DataProvider | Jackson, TestNG |
| Regression | Dedicated UI and API regression suites | TestNG XML |
| Smoke | Small set of critical UI and API checks for fast feedback | TestNG XML |

## 4. Test Data Strategy

- Scenario data is stored in JSON files under `src/main/resources/test-data/QA`.
- Shared users, products, and payment cards are stored separately from feature scenarios to reduce duplication.
- Dynamic values such as unique emails are processed at runtime.
- Cart and checkout tests create isolated temporary users through the API. This prevents tests from sharing cart or order state and supports safe parallel execution.
- Test-created accounts are deleted after use to keep the public sandbox clean.

## 5. Environments and Browsers

| Item | Current status |
|---|---|
| QA | Active configuration and test data |
| Chrome | Supported locally and in Docker |
| Firefox | Supported and smoke-tested locally |

Future environments require both a configuration file and a matching test-data folder; they are not created until a real target environment exists.

## 6. Execution Strategy

- Smoke tests provide fast feedback after a code change.
- UI and API regression suites provide broader coverage.
- UI tests can run in parallel. Each execution thread owns a separate WebDriver through `ThreadLocal<WebDriver>`.
- API-created temporary accounts protect stateful cart and checkout tests from parallel-test interference.
- Docker provides a repeatable headless Chrome execution environment.

## 7. Failure Handling and Evidence

- Retryable failures include Selenium timeouts, stale elements, and temporary connection failures. Retries stop after the configured limit.
- Assertion failures and missing-element failures (`NoSuchElementException`) are not retried.
- Failed UI tests capture a browser screenshot before the driver is closed.
- Test logs, API evidence, environment details, and retry attempts are available in Allure results.

## 8. Entry and Exit Criteria

### Entry criteria

- The target application is reachable.
- Required browser or Docker runtime is available.
- Valid QA configuration and JSON test data exist.

### Exit criteria

- The selected suite completes.
- Test results and Allure artifacts are generated.
- Any failures are reviewed as product defects, test defects, environment issues, or known public-sandbox instability.

## 9. Key Risks and Mitigation

| Risk | Mitigation |
|---|---|
| Public sandbox instability | Explicit waits, targeted retry for transient infrastructure failures, and readable evidence |
| Shared cart or order state | Isolated API-created user per stateful scenario |
| Parallel browser collisions | `ThreadLocal<WebDriver>` and per-thread browser ownership |
| Unclear failures | Structured logs, Allure attachments, screenshots, and retry attempts in Allure |
| Test-data reuse | Shared reference data and runtime-generated unique values |
