# OBS-001 — Guest Sees an Account Deleted Message

| Field | Details |
|---|---|
| Environment | QA — Chrome — 18 October 2026 |
| Classification | UI observation; no account deletion was demonstrated |
| Preconditions | Open a new browser session. Do not log in. |
| Status | Documented |

## Steps to reproduce

1. Open `https://automationexercise.com/delete_account` directly.
2. Do not log in.

## Actual result

The website displays `ACCOUNT DELETED!` and `Your account has been permanently deleted!` to the guest. No account existed in this session, so this observation does not show that an account was deleted.

## Expected result

The website should avoid showing a deletion success message when no account was deleted.

## Evidence

Direct URL tested in a new guest browser session on 18 October 2026.
