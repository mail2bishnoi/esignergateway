package in.gov.rajasthan.risl.esignergateway.service;

import in.gov.rajasthan.risl.esignergateway.model.FlowDecision;

public interface FlowDecisionService {
    FlowDecision decide(String version);
}
