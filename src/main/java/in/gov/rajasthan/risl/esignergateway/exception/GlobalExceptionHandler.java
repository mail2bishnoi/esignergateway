package in.gov.rajasthan.risl.esignergateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Centralized exception handling per section 26 of the project spec.
 * Structural (Bean Validation) failures are handled separately, via
 * BindingResult inside EsignRequestController - deliberately kept apart
 * from business-rule exceptions handled here, so the two failure
 * categories stay visibly distinct in the code.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedVersionException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedVersion(UnsupportedVersionException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "UNSUPPORTED_VERSION", "The requested VERSION is not supported.");
    }

    @ExceptionHandler(InvalidDocumentException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDocument(InvalidDocumentException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "INVALID_DOCUMENT",
                "One or more documents do not match the declared FILE_TYPE, or are not well-formed.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex); // full detail server-side only, never sent to client
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "An unexpected error occurred.");
    }
    @ExceptionHandler(MissingSignerIdException.class)
    public ResponseEntity<Map<String, Object>> handleMissingSignerId(MissingSignerIdException ex) {
        return buildError(HttpStatus.BAD_REQUEST, "MISSING_SIGNER_ID", "SIGNER_ID is required for this VERSION.");
    }
    @ExceptionHandler(InvalidDocumentException.class)
    public ResponseEntity<Map<String, Object>> handleDocumentHash(InvalidDocumentException ex) {
        log.error("Document hashing failed", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "DOCUMENT_HASH_ERROR", "Unable to process document.");
    }
    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String errorCode, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("errorCode", errorCode);
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }

}
