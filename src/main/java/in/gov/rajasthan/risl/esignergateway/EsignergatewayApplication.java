package in.gov.rajasthan.risl.esignergateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EsignergatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsignergatewayApplication.class, args);
    }
}
