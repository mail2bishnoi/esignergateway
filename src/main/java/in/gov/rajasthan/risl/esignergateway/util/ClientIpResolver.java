package in.gov.rajasthan.risl.esignergateway.util;

import in.gov.rajasthan.risl.esignergateway.config.SecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Dedicated component for resolving the true client IP, so X-Forwarded-For
 * is only trusted when the immediate caller is a known/trusted proxy.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientIpResolver {

    private static final String X_FORWARDED_FOR = "X-Forwarded-For";

    private final SecurityProperties securityProperties;

    public String resolve(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();

        if (!securityProperties.getTrustedProxies().contains(remoteAddr)) {
            return remoteAddr;
        }

        String forwardedFor = request.getHeader(X_FORWARDED_FOR);
        if (forwardedFor == null || forwardedFor.isBlank()) {
            return remoteAddr;
        }

        String clientIp = forwardedFor.split(",")[0].trim();
        log.debug("Resolved client IP {} from X-Forwarded-For via trusted proxy {}", clientIp, remoteAddr);
        return clientIp;
    }
}
