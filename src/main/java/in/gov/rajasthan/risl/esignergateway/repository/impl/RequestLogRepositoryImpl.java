package in.gov.rajasthan.risl.esignergateway.repository.impl;

import in.gov.rajasthan.risl.esignergateway.dto.request.MerchantRequestDTO;
import in.gov.rajasthan.risl.esignergateway.model.RequestMetadata;
import in.gov.rajasthan.risl.esignergateway.repository.RequestLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * STUB IMPLEMENTATION.
 *
 * USP_SAVE_ENC_MER_APIREQ_LOG's actual signature has not been provided yet.
 * This stub does NOT touch the database - it only logs that the call would
 * have happened, so the rest of the pipeline can be built and tested
 * end to end without being blocked.
 *
 * TODO: replace this entire class with a real stored-procedure-based
 * implementation once the signature is supplied. RequestLogRepository
 * should not need to change - only this class.
 */
@Slf4j
@Repository
public class RequestLogRepositoryImpl implements RequestLogRepository {

    @Override
    public String saveRequestLog(String requestId, MerchantRequestDTO requestDto, RequestMetadata metadata) {
        log.warn("requestId={} - STUB: USP_SAVE_ENC_MER_APIREQ_LOG not yet implemented "
                        + "(signature not provided). Skipping actual DB persistence. "
                        + "aspId={}, applicationId={}, version={}, ip={}",
                requestId,
                requestDto.getAspId(),
                requestDto.getApplicationId(),
                requestDto.getVersion(),
                metadata.getIpAddress());

        return requestId;
    }
}
