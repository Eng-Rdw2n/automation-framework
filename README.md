# Test Automation Framework

## 1. Project Overview

This repository presents a Java-based **test automation framework** for configuring, executing, and reporting UI and API tests. The main deliverable is the framework itself: its execution model, data management, browser lifecycle, API layer, assertions, logging, retry policy, reporting, and test isolation.

The public [Automation Exercise](https://automationexercise.com) e-commerce sandbox was selected as the application under test so the framework could be applied to realistic UI and API scenarios. The framework architecture and core capabilities are reusable, while Page Objects, locators, application overlays, and scenario data remain specific to the demonstrated application.

> **Project goal:** Build a maintainable and extensible test automation framework, then use system analysis, risk-focused coverage, and automated UI/API checks to demonstrate that it works against a real application.

## 2. Automation Framework Capabilities

The framework provides the following capabilities:

- **Data-driven tests:** scenario data is stored in JSON and supplied through one generic TestNG data provider. Shared users, products, and payment cards reduce repeated data. Unique test values and isolated accounts help prevent tests from interfering with one another.
- **Separate UI and API layers:** UI tests use Page Objects, reusable components, and checkout flows. API tests use REST Assured clients and typed response models; tests explicitly verify both the HTTP status and the application-level `responseCode`.
- **Browser and environment configuration:** Chrome and Firefox are supported. QA is the active environment; the configuration and data layout provide a path for adding another environment later.
- **Controlled parallel execution:** TestNG suites select which tests run together and apply conservative thread limits. Each UI thread receives its own WebDriver instance and download folder.
- **Isolated test data:** tests that modify account, cart, or order state use temporary users created and removed through API setup and cleanup.
- **Failure handling and evidence:** retry rules distinguish known temporary failures from assertions, locator problems, permanent browser errors, and test-code errors. Allure displays retry attempts; logs, screenshots, and failure details are attached as evidence.
- **Readable test output:** Log4j2 records test and framework activity, while shared UI and API assertion helpers provide consistent failure messages.
- **Containerized execution:** Docker runs headless suites with Chromium, giving contributors a consistent execution environment without requiring a local browser installation.
- **Multiple suite levels:** smoke, UI regression, API regression, and framework-validation suites serve different execution and demonstration purposes.

## 3. From System Analysis to Automation

The documents below show how the target system, test scope, sample case designs, automated coverage, and framework implementation fit together. They describe the project's reasoning and traceability, not a record of manual test execution.

```text
System analysis and requirements
              ↓
Test strategy and scope
              ↓
Smoke test-case design
              ↓
Requirements and coverage traceability
              ↓
Framework architecture and implementation
              ↓
Automated UI and API coverage
              ↓
Execution evidence and reporting
```

1. [System analysis and requirements](docs/system-analysis.md) documents the website, UI features, supported API operations, and crucial rules.
2. [Test strategy](docs/test-strategy.md) defines the scope, test levels, browsers, environments, risks, and execution approach.
3. [Smoke test-case samples](docs/test-cases.md) show manual test case designs for selected critical journeys; they do not record manual execution results.
4. [Requirements traceability matrix](docs/traceability-matrix.md) connects features, API operations, and crucial rules to automated tests and highlights coverage gaps.
5. [Sandbox issue reports](docs/bug-reports/) separate reproduced behavior from observations that still need evidence.

### Demonstrated Test Coverage

- **UI:** registration, login, logout, account deletion, product discovery, cart management, checkout, invoice download, contact support, product reviews, and newsletter subscription.
- **API:** products, brands, product search, login verification, and account creation, retrieval, update, and deletion.
- **Validation:** positive journeys, selected negative validations, required fields, invalid credentials, duplicate accounts, and unsupported HTTP methods.

## 4. Technology Stack

| Area | Technology |
|---|---|
| Language | Java 21 |
| Build and test runner | Maven, TestNG |
| UI automation | Selenium WebDriver |
| API automation | REST Assured |
| Test data | Jackson JSON |
| Reporting | Allure |
| Logging | Log4j2 |
| Containerisation | Docker |

## 5. Architecture, Structure, and Framework Design

### Architecture

```text

               JSON test data
                     ↓
          Generic TestNG DataProvider
                     ↓
UI test classes ─────────────── API test classes
      ↓                                ↓
Page Objects and flows          REST Assured clients
      ↓                                ↓
   Web application interface and Public API
                      ↓
Assertions, logs, screenshots, and Allure evidence
```

The framework separates UI and API concerns while sharing configuration, data processing, reporting, logging, and retry behavior.

### Project Structure

<details>
<summary>Expand the source, test, data, suite, and documentation tree</summary>

```text
.
├── .dockerignore
├── .gitignore
├── Dockerfile
├── pom.xml
├── README.md
├── docs/
│   ├── bug-reports/
│   │   ├── OBS-001-guest-sees-account-deleted-message.md
│   │   ├── BUG-002-guest-can-open-checkout-page.md
│   │   └── bug-report-template.md
│   ├── images/use-case-diagram.png
│   ├── system-analysis.md
│   ├── test-cases.md
│   ├── test-strategy.md
│   └── traceability-matrix.md
├── src/
│   ├── main/java/com/radwanalbunni/automation/
│   │   ├── api/
│   │   │   ├── clients/
│   │   │   │   ├── AccountClient.java
│   │   │   │   ├── ApiClientBase.java
│   │   │   │   ├── BrandClient.java
│   │   │   │   └── ProductClient.java
│   │   │   ├── endpoints/Endpoints.java
│   │   │   ├── specs/RequestSpecs.java
│   │   │   └── ResponseParser.java
│   │   ├── assertions/
│   │   │   ├── ApiAssertions.java
│   │   │   └── UiAssertions.java
│   │   ├── core/
│   │   │   ├── base/
│   │   │   │   └── UiBaseTest.java
│   │   │   ├── config/ConfigReader.java
│   │   │   ├── driver/
│   │   │   │   ├── BrowserDriver.java
│   │   │   │   ├── ChromeDriverCreator.java
│   │   │   │   ├── DriverFactory.java
│   │   │   │   └── FirefoxDriverCreator.java
│   │   │   ├── listeners/
│   │   │   │   ├── AnnotationTransformer.java
│   │   │   │   └── TestListener.java
│   │   │   ├── logging/
│   │   │   │   ├── LoggingContext.java
│   │   │   │   ├── SensitiveDataMasker.java
│   │   │   │   └── TestLogger.java
│   │   │   ├── reporting/
│   │   │   │   ├── AllureAttachments.java
│   │   │   │   └── AllureReportSetup.java
│   │   │   ├── retry/
│   │   │   │   ├── FailureClassifier.java
│   │   │   │   ├── FailureType.java
│   │   │   │   ├── RetryAnalyzer.java
│   │   │   └── screenshot/ScreenshotService.java
│   │   ├── data/
│   │   │   ├── annotations/
│   │   │   │   ├── TestDataFile.java
│   │   │   │   └── TestDataType.java
│   │   │   ├── models/domain/
│   │   │   │   ├── ApiScenarioDataModel.java
│   │   │   │   ├── CartDataModel.java
│   │   │   │   ├── CheckoutDataModel.java
│   │   │   │   ├── ContactUsDataModel.java
│   │   │   │   ├── LoginDataModel.java
│   │   │   │   ├── PaymentCardDataModel.java
│   │   │   │   ├── ProductDataModel.java
│   │   │   │   ├── RegistrationDataModel.java
│   │   │   │   ├── ReviewDataModel.java
│   │   │   │   ├── SearchDataModel.java
│   │   │   │   ├── SubscriptionDataModel.java
│   │   │   │   ├── TestDataModel.java
│   │   │   │   └── UserDataModel.java
│   │   │   ├── models/response/
│   │   │   │   ├── ApiMessageResponse.java
│   │   │   │   ├── BrandsResponse.java
│   │   │   │   ├── ProductsResponse.java
│   │   │   │   └── UserDetailsResponse.java
│   │   │   ├── DataProcessor.java
│   │   │   ├── factory/
│   │   │   │   ├── TestUserFactory.java
│   │   │   │   └── UniqueDataFactory.java
│   │   │   ├── GenericDataProvider.java
│   │   │   ├── ReferenceDataStore.java
│   │   │   ├── ResolvableTestData.java
│   │   │   └── TestDataLoader.java
│   │   └── ui/
│   │       ├── components/
│   │       │   ├── Footer.java
│   │       │   └── Header.java
│   │       ├── flows/CheckoutFlow.java
│   │       ├── pages/
│   │       │   ├── AccountPage.java
│   │       │   ├── BasePage.java
│   │       │   ├── CartPage.java
│   │       │   ├── CheckoutPage.java
│   │       │   ├── ContactUsPage.java
│   │       │   └── ProductsPage.java
│   │       └── utils/
│   │           ├── DownloadUtils.java
│   │           └── WaitUtils.java
│   ├── main/resources/
│   │   ├── config/qa.properties
│   │   ├── categories.json
│   │   ├── log4j2.xml
│   │   ├── test-files/img.png
│   │   └── test-data/QA/
│   │       ├── api/
│   │       │   ├── accounts.json
│   │       │   ├── brands.json
│   │       │   ├── login.json
│   │       │   ├── products.json
│   │       │   └── registration.json
│   │       ├── shared/
│   │       │   ├── paymentCards.json
│   │       │   ├── products.json
│   │       │   └── users.json
│   │       └── ui/
│   │           ├── cart.json
│   │           ├── checkout.json
│   │           ├── contact.json
│   │           ├── login.json
│   │           ├── registration.json
│   │           ├── review.json
│   │           ├── search.json
│   │           └── subscription.json
│   └── test/java/com/radwanalbunni/tests/
│       ├── api/
│       │   ├── AccountApiTest.java
│       │   ├── BrandsApiTest.java
│       │   ├── LoginApiTest.java
│       │   ├── ProductsApiTest.java
│       │   ├── RegistrationApiTest.java
│       │   └── SearchApiTest.java
│       ├── ui/
│       │   ├── CartUiTest.java
│       │   ├── CheckoutUiTest.java
│       │   ├── ContactUsUiTest.java
│       │   ├── LoginUiTest.java
│       │   ├── RegistrationUiTest.java
│       │   ├── ReviewUiTest.java
│       │   ├── SearchUiTest.java
│       │   └── SubscriptionUiTest.java
│       └── validation/FrameworkValidationTest.java
└── suites/
    ├── api-regression.xml
    ├── framework-validation.xml
    ├── smoke.xml
    └── ui-regression.xml
```
</details>

### How the Framework Works

### Retry, Failure Classification, and Evidence

Each failed attempt passes through `FailureClassifier`. `RetryAnalyzer` retries only known temporary failures—timeouts, stale elements, intercepted clicks, socket timeouts, and connection failures—up to the configured limit. If the current page contains the configured overload message, it waits briefly before retrying; other retries remain immediate. Assertions, missing elements, invalid selectors or arguments, session failures, generic WebDriver failures, and test-code errors stop without retrying. The Allure TestNG integration records the attempts and displays them in the test's Retries tab. When a test finally fails, `TestListener` attaches the failure type, test log, and—when a browser is active—a screenshot.

```text
                    ┌────────────────────┐
                    │ Test attempt fails │
                    └─────────┬──────────┘
                              ↓
                    ┌────────────────────┐
                    │ Classify failure   │
                    └─────────┬──────────┘
                     ┌────────┴────────────┐
                     │                     │
       Permanent or test failure       Known temporary failure
                     │                     │
                     ↓                     ↓
             ┌───────────────┐     ┌──────────────────┐
             │ Stop: failed  │     │ Retries left?    │
             └───────┬───────┘     └────────┬─────────┘
                     │                yes   │   no
                     │          ┌───────────┘    └──────────┐
                     │          ↓                           ↓
                     │   ┌───────────────┐         ┌────────────────┐
                     │   │ Run again ↺   │         │ Final failure  │
                     │   └───────────────┘         └───────┬────────┘
                     │                                     ↓
                     └─────────────────── Screenshot + log attached; Allure displays retries
```

The browser is closed by `UiBaseTest` after the listener has had a chance to capture the failure state.

### Test Data Flow

The test method's annotations select a JSON file and scenario type. The method parameter tells the generic provider which model to create. Shared JSON stores reusable users, products, and cards. `DataProcessor` creates an isolated scenario copy, replaces dynamic values, and asks reference-aware models to load typed shared data through `ReferenceDataStore`.

```text
┌───────────────────┐     ┌────────────────────┐
│ Scenario JSON     │     │ Shared JSON        │
│ action + overrides│     │ users/products/cards│
└─────────┬─────────┘     └──────────┬─────────┘
          └────────────┬─────────────┘
                       ↓
             ┌─────────────────────┐
             │ GenericDataProvider │  reads annotations + model type
             └──────────┬──────────┘
                        ↓
             ┌─────────────────────┐
             │ TestDataLoader      │  reads and caches JSON records
             └──────────┬──────────┘
                        ↓
             ┌─────────────────────┐
             │ DataProcessor       │  copies → fills dynamic values → resolves typed IDs
             └──────────┬──────────┘
                        ↓
             ┌─────────────────────┐
             │ Typed test method   │  receives its model object
             └─────────────────────┘
```

For example, another invalid-login input can usually be added to `ui/login.json` and handled by the existing login test/model/provider. A new model is only needed when a feature needs a different data shape.

### Parallel Execution

Reliable parallel execution requires test isolation: every test should be self-contained, control its own data and state, and not depend on another test's order or result. Shared accounts or carts can make tests interfere with one another and produce unreliable results.

```text
                         Test runner
                 ┌──────────┼──────────┐
                 ↓          ↓          ↓
              Thread 1   Thread 2   Thread 3
                 │          │          │
              Test A     Test B     Test C
                 │          │          │
              Data A     Data B     Data C

UI test:      Thread 1 → Browser 1     Thread 2 → Browser 2
API test:     Thread 1 → Request 1     Thread 2 → Request 2
```

Each active UI thread owns its WebDriver and download location. Tests that change account or cart state use isolated accounts and clean them up afterward, so parallel tests do not share that state.

The thread count can be increased when the test runner has enough CPU and memory and the target application or API can handle more concurrent traffic. Increase it gradually and monitor run time and failures: more threads do not always mean a faster or more stable run.

### CI and Stakeholder Reporting Flow

GitHub Actions runs the smoke suite on pushes to `main`, pull requests, and manual requests. It preserves raw evidence, generates an Allure report even when tests fail, and publishes the report on GitHub Pages after Pages is enabled. Later runs restore Allure history from the previous run. An optional email sends the result and links when the mail secrets are configured. See [CI reports and notifications](docs/ci-reporting.md) for setup and limits. The published report and email delivery still need a successful live verification.

## 6. Setup, Configuration, and Execution

### Test Suites

| Suite | Purpose | Execution |
|---|---|---|
| `smoke.xml` | Critical UI and API checks | API and UI blocks run in parallel |
| `ui-regression.xml` | Full UI coverage | Parallel by class, up to 2 threads |
| `api-regression.xml` | Full API coverage | Parallel by class, up to 2 threads |
| `framework-validation.xml` | Checks the framework's retry, failure classification, screenshot, log, and Allure behavior | Run separately on demand |

### Framework Validation

This suite tests the framework itself, rather than the website's behavior. It deliberately triggers selected failures to check retry decisions, failure classification, screenshots, logs, and Allure attachments. These expected failures make Maven report `BUILD FAILURE`; they do not mean the smoke or regression suites failed. Run this suite separately when reviewing framework behavior. It is not part of the default smoke or regression runs or the CI workflow.

Run it with:

```bash
mvn test -DsuiteXmlFile=suites/framework-validation.xml -Dbrowser=firefox -Dheadless=false
```

Check the console and `target/logs` for retry and test logs. In Allure, check the Retries tab for retry attempts and open deliberately failed tests to inspect their failure type, screenshot, and attached test log.

### Run Locally

Prerequisites: Java 21, Maven, and Chrome or Firefox. Docker is optional.

```bash
# Default: QA smoke suite in Chrome
mvn test

# Smoke suite in headless mode
mvn test -Dheadless=true

# Full UI regression
mvn test -DsuiteXmlFile=suites/ui-regression.xml -Dheadless=true

# Full API regression
mvn test -DsuiteXmlFile=suites/api-regression.xml

# Firefox smoke suite
mvn test -Dbrowser=firefox -Dheadless=true
```
### Run with Docker

```bash
# Build the image
docker build -t automation-framework:local .

# Default: run the headless smoke suite and keep results on the host
docker run --rm --shm-size=2g \
  -v "$PWD/target:/app/target" \
  automation-framework:local

# Run the UI regression suite
docker run --rm --shm-size=2g \
  -v "$PWD/target:/app/target" \
  automation-framework:local \
  -DsuiteXmlFile=suites/ui-regression.xml

# Run the API regression suite
docker run --rm --shm-size=2g \
  -v "$PWD/target:/app/target" \
  automation-framework:local \
  -DsuiteXmlFile=suites/api-regression.xml
```

The image defaults to Smoke. Change the `suiteXmlFile` value to the suite you want to run; the matching XML file must be included in the image.

The current Docker image contains Chromium only. Firefox runs are supported locally; add a separate Firefox image or Selenium Grid only when containerized cross-browser execution is required.

### Allure Report

After a test run:

```bash
mvn allure:serve
```

Custom failure categories are loaded from `src/main/resources/categories.json` into `target/allure-results` at suite start. They group assertion, locator, temporary infrastructure, permanent browser/automation, and other test-code failures in the Allure Categories view without changing the test result or retry behavior.

### Add an Environment Later

QA is the only active environment today. To add Staging later, add both:

```text
src/main/resources/config/staging.properties
src/main/resources/test-data/STAGING/
```

Then run:

```bash
mvn test -Denv=staging
```


Raw Allure results, TestNG output, logs, screenshots, and downloads are generated under `target/`.

## 7. Framework Verification and Evidence

This section will present final execution evidence after the local suites and CI workflow have been verified. It will include a small set of readable screenshots rather than generated report folders committed to the repository.

Planned evidence:

- A successful smoke or regression-suite overview.
- Parallel execution visible in the Allure timeline.
- A controlled framework-validation failure with its classification, screenshot, and test log.
- A retried test showing its attempts in the Allure Retries tab.
- A successful GitHub Actions workflow and published report link.
- Verified stakeholder report delivery after CI is implemented.

Until that evidence is captured, the commands in the previous section are the reproducible way to verify the framework locally.

## 8. Future Work

- Add separate CI execution for the regression suites when their runtime and target environment support it.
- Verify GitHub Pages publication and stakeholder email delivery after the required repository settings and mail secrets are configured.
- Add containerized Firefox execution or Selenium Grid only when Docker-based cross-browser coverage is required.
- Add another environment only when its configuration and environment-specific test data are available.

## Notes

This framework targets a public sandbox. Credentials are test-only data. In a production project, credentials would come from environment variables or a secrets manager and would never be committed to source control.
