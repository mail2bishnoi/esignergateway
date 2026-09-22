package in.gov.rajasthan.risl.esignergateway.service;

import in.gov.rajasthan.risl.esignergateway.model.RequestMetadata;
import jakarta.servlet.http.HttpServletRequest;

public interface RequestMetadataService {
    RequestMetadata capture(HttpServletRequest request);
}
