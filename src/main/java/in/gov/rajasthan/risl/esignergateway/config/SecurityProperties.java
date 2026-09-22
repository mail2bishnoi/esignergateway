package in.gov.rajasthan.risl.esignergateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * IP addresses of trusted reverse proxies / load balancers sitting in front
 * of this application. Only when the immediate connecting socket address
 * matches an entry here is X-Forwarded-For considered trustworthy.
 *
 * ASSUMPTION: defaults to an empty list - until infrastructure topology is
 * confirmed, X-Forwarded-For is NOT trusted, and the raw socket IP is
 * always used instead.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "esign.security")
public class SecurityProperties {
    private List<String> trustedProxies = List.of();
}
