# CI Reports and Notifications

The smoke workflow runs on pushes to `main`, pull requests, and manual requests. A failed test keeps the smoke job red. Report generation still runs so the failure remains visible in Allure.

## First-time GitHub setup

1. Open the repository's **Settings → Pages**.
2. Under **Build and deployment**, choose **GitHub Actions** as the source.
3. Push the workflow change or run **Actions → Smoke Tests → Run workflow** on `main`.
4. Open the `publish` job to find the report URL. GitHub Pages is public for this public repository, including report attachments. Use test-only data and review attachments before sharing the URL.

The report is also saved as the `allure-report` workflow artifact for seven days. Raw results, test logs, and TestNG output remain in the `smoke-evidence` artifact.

## History

After a run on `main`, the workflow caches the report's `history` directory. The next run copies it into `allure-results` before generating the new report. The first report has no prior history; the History and Trend views start filling after subsequent runs. GitHub may evict caches, so this is a convenient trend history, not permanent archival.

## Email

In **Settings → Secrets and variables → Actions**, add these repository secrets:

| Secret | Meaning |
|---|---|
| `SMTP_HOST` | Outgoing mail server hostname |
| `SMTP_PORT` | TLS mail server port, usually `587` |
| `SMTP_USERNAME` | Mailbox used to send the message |
| `SMTP_PASSWORD` | Mailbox app password or SMTP credential |
| `REPORT_RECIPIENT` | Stakeholder email address |

When all five secrets exist, the `notify` job sends the smoke result, report URL, and workflow-run URL after each run on `main`. Until then it prints a setup notice and sends nothing. Never commit mail credentials to this repository. A failed smoke test is reported as failed in the email; the workflow does not turn it green.
