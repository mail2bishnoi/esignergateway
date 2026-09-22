package in.gov.rajasthan.risl.esignergateway.repository;

import in.gov.rajasthan.risl.esignergateway.dto.request.MerchantRequestDTO;
import in.gov.rajasthan.risl.esignergateway.model.RequestMetadata;

/**
 * Persists the incoming merchant request via USP_SAVE_ENC_MER_APIREQ_LOG.
 *
 * ASSUMPTION: the actual stored procedure signature has not yet been
 * provided. See RequestLogRepositoryImpl - currently a stub.
 */
public interface RequestLogRepository {
    String saveRequestLog(String requestId, MerchantRequestDTO requestDto, RequestMetadata metadata);
}
