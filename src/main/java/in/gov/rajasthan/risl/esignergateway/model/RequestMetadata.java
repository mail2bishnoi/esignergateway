package in.gov.rajasthan.risl.esignergateway.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * Internal carrier for HTTP request metadata captured for every incoming
 * request. Not a DTO - never serialized directly to/from the API boundary.
 */
@Getter
@Builder
public class RequestMetadata {
    private final String ipAddress;
    private final String referrer;
    private final String httpMethod;
    private final String serverUrl;
    private final String userAgent;
    private final String requestUri;
    private final Instant timestamp;
}
