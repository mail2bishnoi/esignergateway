package in.gov.rajasthan.risl.esignergateway.service.impl;

import in.gov.rajasthan.risl.esignergateway.dto.request.MerchantRequestDTO;
import in.gov.rajasthan.risl.esignergateway.model.FlowDecision;
import in.gov.rajasthan.risl.esignergateway.model.ProcessingResult;
import in.gov.rajasthan.risl.esignergateway.model.RequestMetadata;
import in.gov.rajasthan.risl.esignergateway.repository.RequestLogRepository;
import in.gov.rajasthan.risl.esignergateway.service.EsignRedirectionService;
import in.gov.rajasthan.risl.esignergateway.service.FlowDecisionService;
import in.gov.rajasthan.risl.esignergateway.service.RequestMetadataService;
import in.gov.rajasthan.risl.esignergateway.validation.DocumentValidator;
import in.gov.rajasthan.risl.esignergateway.validation.SignerIdValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EsignRedirectionServiceImpl implements EsignRedirectionService {

    private final RequestMetadataService requestMetadataService;
    private final RequestLogRepository requestLogRepository;
    private final DocumentValidator documentValidator;
    private final FlowDecisionService flowDecisionService;
    private final SignerIdValidator signerIdValidator;

    @Override
    public ProcessingResult processRequest(MerchantRequestDTO requestDto, HttpServletRequest httpServletRequest) {
        String requestId = UUID.randomUUID().toString();

        RequestMetadata metadata = requestMetadataService.capture(httpServletRequest);

        log.info("requestId={} - processing request: aspId={}, applicationId={}, version={}, fileType={}, "
                        + "documentCount={}, ip={}",
                requestId,
                requestDto.getAspId(),
                requestDto.getApplicationId(),
                requestDto.getVersion(),
                requestDto.getFileType(),
                requestDto.getDocuments() == null ? 0 : requestDto.getDocuments().size(),
                metadata.getIpAddress());

        requestLogRepository.saveRequestLog(requestId, requestDto, metadata);

        // NOT YET IMPLEMENTED: SECRET_KEY / ASP_ID / APPLICATION_ID / IP
        // validation - paused to build/test fileType-based document
        // validation first. Must be inserted here, before document
        // validation, once resumed (per section 8's mandated order).

        documentValidator.validate(requestDto.getFileType(), requestDto.getDocuments());

        signerIdValidator.validate(requestDto.getVersion(), requestDto.getSignerId());


        FlowDecision flowDecision = flowDecisionService.decide(requestDto.getVersion());

        log.info("requestId={} - flow decided: type={}, redirectUrl={}",
                requestId, flowDecision.getFlowType(), flowDecision.getRedirectUrl());

        return ProcessingResult.builder()
                .requestId(requestId)
                .success(true)
                .flowType(flowDecision.getFlowType())
                .redirectUrl(flowDecision.getRedirectUrl())
                .redirectParams(Map.of(
                "aspId", requestDto.getAspId(),
                "applicationId", requestDto.getApplicationId()
                )).build();
    }
}
