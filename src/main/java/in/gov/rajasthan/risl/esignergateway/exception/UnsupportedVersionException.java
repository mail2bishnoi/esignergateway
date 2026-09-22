package in.gov.rajasthan.risl.esignergateway.exception;

public class UnsupportedVersionException extends RuntimeException {
    public UnsupportedVersionException(String version) {
        super("Unsupported or unconfigured VERSION: " + version);
    }
}
