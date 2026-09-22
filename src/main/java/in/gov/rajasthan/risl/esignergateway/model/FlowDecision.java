package in.gov.rajasthan.risl.esignergateway.model;

import in.gov.rajasthan.risl.esignergateway.enums.FlowType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FlowDecision {
    private final FlowType flowType;
    private final String redirectUrl;
}
