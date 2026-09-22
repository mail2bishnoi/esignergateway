package in.gov.rajasthan.risl.esignergateway.service.impl;

import in.gov.rajasthan.risl.esignergateway.config.VersionFlowProperties;
import in.gov.rajasthan.risl.esignergateway.exception.UnsupportedVersionException;
import in.gov.rajasthan.risl.esignergateway.model.FlowDecision;
import in.gov.rajasthan.risl.esignergateway.service.FlowDecisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowDecisionServiceImpl implements FlowDecisionService {

    private final VersionFlowProperties versionFlowProperties;

    @Override
    public FlowDecision decide(String version) {
        VersionFlowProperties.VersionConfig config = versionFlowProperties.getVersions().stream()
                .filter(v -> v.getVersion().equals(version))
                .findFirst()
                .orElseThrow(() -> new UnsupportedVersionException(version));

        return FlowDecision.builder()
                .flowType(config.getFlow())
                .redirectUrl(config.getUrl())
                .build();
    }
}