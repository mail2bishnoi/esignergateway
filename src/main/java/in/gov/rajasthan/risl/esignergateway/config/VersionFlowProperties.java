package in.gov.rajasthan.risl.esignergateway.config;

import in.gov.rajasthan.risl.esignergateway.enums.FlowType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Binds esign.versions from application.yml as a LIST, not a Map keyed by
 * version string. This is deliberate: Spring's relaxed YAML binding treats
 * dots in map keys as nested-path separators (e.g. "2.1" would silently
 * bind as path 2 -> 1, not the literal key "2.1"), which made numeric
 * versions unmatchable. A list with an explicit "version" field per entry
 * avoids this entirely, for any future version string.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "esign")
public class VersionFlowProperties {

    private List<VersionConfig> versions;

    @Getter
    @Setter
    public static class VersionConfig {
        private String version;
        private FlowType flow;
        private String url;
    }
}