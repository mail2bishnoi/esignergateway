package in.gov.rajasthan.risl.esignergateway.service;

import in.gov.rajasthan.risl.esignergateway.dto.request.MerchantRequestDTO;
import in.gov.rajasthan.risl.esignergateway.model.ProcessingResult;
import jakarta.servlet.http.HttpServletRequest;

public interface EsignRedirectionService {

    /**
     * Orchestrates the request-processing pipeline: request ID generation,
     * metadata capture, request logging, document validation, and flow
     * decision. SECRET_KEY / ASP_ID / APPLICATION_ID / IP validation are
     * not yet implemented - see project plan for current status.
     */
    ProcessingResult processRequest(MerchantRequestDTO requestDto, HttpServletRequest httpServletRequest);
}
