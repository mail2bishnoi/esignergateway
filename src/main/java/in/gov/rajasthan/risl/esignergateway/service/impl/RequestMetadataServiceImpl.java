package in.gov.rajasthan.risl.esignergateway.service.impl;

import in.gov.rajasthan.risl.esignergateway.model.RequestMetadata;
import in.gov.rajasthan.risl.esignergateway.service.RequestMetadataService;
import in.gov.rajasthan.risl.esignergateway.util.ClientIpResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RequestMetadataServiceImpl implements RequestMetadataService {

    private final ClientIpResolver clientIpResolver;

    @Override
    public RequestMetadata capture(HttpServletRequest request) {
        return RequestMetadata.builder()
                .ipAddress(clientIpResolver.resolve(request))
                .referrer(request.getHeader("Referer"))
                .httpMethod(request.getMethod())
                .serverUrl(request.getRequestURL().toString())
                .userAgent(request.getHeader("User-Agent"))
                .requestUri(request.getRequestURI())
                .timestamp(Instant.now())
                .build();
    }
}
