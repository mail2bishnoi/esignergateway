# eSignerGateway

Redirection-based eSign request flow — Spring Boot 4.1.1, Java 17.

## How to import

1. Unzip this project.
2. In IntelliJ: File → Open → select the unzipped `esignergateway` folder
   (the one containing `pom.xml`).
3. Let Maven download dependencies (needs internet access — this project
   was not built/verified inside a sandboxed environment, so please run
   a full `mvn clean compile` or let IntelliJ build it yourself the first
   time, and report back anything that doesn't come up clean).
4. Run `EsignergatewayApplication.main()`.
5. Confirm `GET http://localhost:8080/ping` returns 200.

## What's implemented

- Structural request validation (Bean Validation on `MerchantRequestDTO`)
- `fileType` (PDF / XML / DATA) + document content validation
  (`DocumentValidator`) — including XXE-safe XML parsing
- Request metadata capture (IP, headers, timestamp) with a dedicated,
  proxy-aware `ClientIpResolver`
- VERSION-based flow decision (`FlowDecisionService`), externalized to
  `application.yml` under `esign.versions`
- REDIRECT flow implemented as an auto-submitting POST form (Thymeleaf),
  carrying only `requestId` back to the merchant for later webhook
  correlation
- Centralized exception handling (`GlobalExceptionHandler`) for business
  exceptions; structural validation errors are handled separately via
  `BindingResult` in the controller
- `USP_SAVE_ENC_MER_APIREQ_LOG` — stubbed only (logs a warning), since the
  real stored procedure signature has not been provided yet

## Deliberately NOT implemented yet (paused, not forgotten)

- SECRET_KEY / ASP_ID / APPLICATION_ID / IP validation (Phase 3)
- Any database persistence (H2 or Oracle) — this build has zero DB
  dependencies by design, to keep this baseline minimal and testable
- UI flow / PREVIEW page rendering (currently returns JSON instead)
- `MerchantRequestJsonDTO` exists as a backup (base64/JSON transport
  variant) but is not wired to any endpoint

## Testing from Postman

`POST http://localhost:8080/api/esign/redirect`, Body → form-data:

| Key | Type | Example |
|---|---|---|
| aspId | Text | 101 |
| applicationId | Text | 1 |
| version | Text | 2.1 |
| encryptedData | Text | any non-blank string |
| fileType | Text | PDF / XML / DATA |
| documents | File | matching file(s), up to 5 |
