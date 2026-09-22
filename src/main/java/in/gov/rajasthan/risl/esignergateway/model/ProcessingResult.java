package in.gov.rajasthan.risl.esignergateway.model;

import in.gov.rajasthan.risl.esignergateway.enums.FlowType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * Internal outcome of processing a merchant request. Not a DTO - the
 * Controller translates this into the actual HTTP response/redirect.
 */
@Getter
@Builder
public class ProcessingResult {
    private final String requestId;
    private final boolean success;
    private final FlowType flowType;
    private final String redirectUrl;

    /**
     * Parameters handed back to the merchant on redirect (currently just
     * requestId), so their page can correlate a later webhook callback to
     * this transaction.
     */
    private final Map<String, String> redirectParams;
}
