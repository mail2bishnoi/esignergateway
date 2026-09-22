package in.gov.rajasthan.risl.esignergateway.validation.impl;

import in.gov.rajasthan.risl.esignergateway.exception.MissingSignerIdException;
import in.gov.rajasthan.risl.esignergateway.validation.SignerIdValidator;
import org.springframework.stereotype.Component;

/**
 * SIGNER_ID is required only for VERSION 3.2. Hardcoded here rather than
 * externalized to configuration, since this is a fixed, specific rule for
 * one named version — not a pattern expected to generalize across many
 * versions.
 */
@Component
public class SignerIdValidatorImpl implements SignerIdValidator {

    private static final String VERSION_REQUIRING_SIGNER_ID = "3.2";

    @Override
    public void validate(String version, String signerId) {
        if (VERSION_REQUIRING_SIGNER_ID.equals(version) && (signerId == null || signerId.isBlank())) {
            throw new MissingSignerIdException("SIGNER_ID is required for VERSION 3.2");
        }
    }
}