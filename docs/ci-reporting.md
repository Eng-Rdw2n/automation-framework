# CI Reports and Notifications

The workflow runs smoke on pushes to `main`, pull requests from the same repository, and every Monday at 09:17 in the `Asia/Dubai` time zone. Manual runs can select `smoke` or `full-regression`; the combined suite runs API tests and then UI tests in one Maven invocation and one Allure report. Fork pull requests are skipped because they cannot access the test account secrets. A failed test keeps the test job red. Report generation still runs so the failure remains visible in Allure. GitHub may delay scheduled runs.

## First-time GitHub setup

1. Open the repository's **Settings → Pages**.
2. Under **Build and deployment**, choose **GitHub Actions** as the source.
3. Push the workflow change or run **Actions → Test Automation → Run workflow** on `main`. Select the suite for a manual run.
4. Open the `publish` job to find the report URL. GitHub Pages is public for this public repository, including report attachments. Use test-only data and review attachments before sharing the URL.

Before running tests, add `TEST_STANDARD_USER_EMAIL` and `TEST_STANDARD_USER_PASSWORD` as repository secrets under **Settings → Secrets and variables → Actions**. These hold the existing test account credentials. The workflow passes them to the test process; they are not stored in the scenario JSON. Keep the values out of logs and Allure attachments.

The report is also saved as the `allure-report` workflow artifact for seven days. Raw results, test logs, and TestNG output remain in the `test-evidence` artifact.

## History

After each report on `main`, the workflow saves the generated `history` JSON files on a separate branch: `allure-history` for smoke and `allure-history-full-regression` for the combined suite. The next run of the same suite copies its history into `allure-results` before generating the new report. Each branch is created on its first run and holds only rolling Allure history data, not copies of past screenshots, logs, or full reports. Allure 2 shows up to the latest 20 reports in History and Trend. The first run of each suite has no prior history. GitHub Pages shows the latest published report, whichever suite produced it; older reports remain available as workflow artifacts until they expire.

## Email

In **Settings → Secrets and variables → Actions**, add these repository secrets:

| Secret | Meaning |
|---|---|
| `SMTP_HOST` | Outgoing mail server hostname |
| `SMTP_PORT` | TLS mail server port, usually `587` |
| `SMTP_USERNAME` | Mailbox used to send the message |
| `SMTP_PASSWORD` | Mailbox app password or SMTP credential |
| `REPORT_RECIPIENT` | Stakeholder email address |

The workflow runs every Monday to keep a fresh report and history. Starting on 23 November 2026, the `notify` job sends an email every second scheduled Monday. A manual run can send a test email when **Send a test email after this manual run** is selected. Pushes do not send email. The message includes the test, publication, and history-storage results; test counts when Allure produced a report; and links to the report and workflow run. Set `REPORT_RECIPIENT` to your own address for the first live test. It may be the same address as `SMTP_USERNAME`. Without all five mail secrets, the job prints a setup notice and sends nothing. Never commit mail credentials to this repository; a failed test remains a failed workflow.

The email identifies the suite and says `PASSED` only when the selected tests, report publication, and history storage all succeed. It says `FAILED` if any of those jobs fails or is cancelled, and `INCOMPLETE` if a required job is skipped. The message lists each job's result so the recipient can see what happened.
