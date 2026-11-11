"""Send a short CI result email without storing credentials in the repository."""

import os
import smtplib
import ssl
from email.message import EmailMessage


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
    result = os.environ["RUN_RESULT"].upper()
    run_url = os.environ["RUN_URL"]
    report_url = os.environ.get("REPORT_URL") or "Report unavailable; see the workflow run."

    message = EmailMessage()
    message["Subject"] = f"Automation framework smoke tests: {result}"
    message["From"] = os.environ["SMTP_USERNAME"]
    message["To"] = os.environ["REPORT_RECIPIENT"]
    message.set_content(
        f"Smoke test run: {result}\n"
        f"Allure report: {report_url}\n"
        f"GitHub Actions run: {run_url}\n"
    )

    with smtplib.SMTP(os.environ["SMTP_HOST"], int(os.environ["SMTP_PORT"]), timeout=30) as server:
        server.starttls(context=ssl.create_default_context())
        server.login(os.environ["SMTP_USERNAME"], os.environ["SMTP_PASSWORD"])
        server.send_message(message)

    print("Stakeholder result email sent.")
