package in.gov.rajasthan.risl.esignergateway.exception;

public class MissingSignerIdException extends RuntimeException {
    public MissingSignerIdException(String message) {
        super(message);
    }
}