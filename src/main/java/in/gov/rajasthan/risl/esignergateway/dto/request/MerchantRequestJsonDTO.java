package in.gov.rajasthan.risl.esignergateway.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * BACKUP / not currently wired to any endpoint.
 *
 * Alternate request shape for the eSign redirection flow, used only if the
 * merchant integration ever moves away from multipart/form-data back to a
 * JSON body with Base64-encoded document content.
 */
@Getter
@Setter
@ToString(exclude = {"encryptedData", "documents"})
public class MerchantRequestJsonDTO {

    @NotBlank(message = "ASP_ID is required")
    private String aspId;

    @NotBlank(message = "APPLICATION_ID is required")
    private String applicationId;

    @NotBlank(message = "VERSION is required")
    @Pattern(
            regexp = "^([0-9]+\\.[0-9]+|PREVIEW)$",
            message = "VERSION must be numeric (e.g. 2.1) or PREVIEW"
    )
    private String version;

    @NotBlank(message = "ENCRYPTED_DATA is required")
    private String encryptedData;

    @NotEmpty(message = "At least one document is required")
    @Size(max = 5, message = "A maximum of 5 documents is allowed")
    private List<@NotBlank(message = "document entries must not be blank") String> documents;
}
