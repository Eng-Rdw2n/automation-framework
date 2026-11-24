"""Send a short CI result email without storing credentials in the repository."""

import json
import os
import smtplib
import ssl
from email.message import EmailMessage
from pathlib import Path


required = (
    "SMTP_HOST",
    "SMTP_PORT",
    "SMTP_USERNAME",
    "SMTP_PASSWORD",
    "REPORT_RECIPIENT",
)
missing = [name for name in required if not os.environ.get(name)]

if missing:
    print("Email not sent: configure the required repository secrets first.")
else:
    test_result = os.environ["RUN_RESULT"]
    suite_name = os.environ.get("SUITE_NAME") or "smoke"
    publish_result = os.environ.get("PUBLISH_RESULT", "skipped")
    history_result = os.environ.get("HISTORY_RESULT", "skipped")
    job_results = (test_result, publish_result, history_result)
    if all(job_result == "success" for job_result in job_results):
        result = "PASSED"
    elif any(job_result in ("failure", "cancelled") for job_result in job_results):
        result = "FAILED"
    else:
        result = "INCOMPLETE"
    run_url = os.environ["RUN_URL"]
    report_url = os.environ.get("REPORT_URL") or "Report unavailable; see the workflow run."

    summary_path = Path("site/widgets/summary.json")
    counts = "Test counts unavailable."
    if summary_path.exists():
        try:
            statistics = json.loads(summary_path.read_text(encoding="utf-8"))["statistic"]
            counts = (
                f"Tests: {statistics['total']} total, {statistics['passed']} passed, "
                f"{statistics['failed']} failed, {statistics['broken']} broken, "
                f"{statistics['skipped']} skipped."
            )
        except (OSError, ValueError, KeyError):
            pass

    message = EmailMessage()
    message["Subject"] = f"Automation framework report: {result}"
    message["From"] = os.environ["SMTP_USERNAME"]
    message["To"] = os.environ["REPORT_RECIPIENT"]
    message.set_content(
        f"Overall result: {result}\n"
        f"Test suite: {suite_name}\n"
        f"Test result: {test_result.upper()}\n"
        f"Report publication: {publish_result.upper()}\n"
        f"History storage: {history_result.upper()}\n"
        f"{counts}\n"
        f"Allure report: {report_url}\n"
        f"GitHub Actions run: {run_url}\n"
    )

    with smtplib.SMTP(os.environ["SMTP_HOST"], int(os.environ["SMTP_PORT"]), timeout=30) as server:
        server.starttls(context=ssl.create_default_context())
        server.login(os.environ["SMTP_USERNAME"], os.environ["SMTP_PASSWORD"])
        server.send_message(message)

    print("Stakeholder result email sent.")
