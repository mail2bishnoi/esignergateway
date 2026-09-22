package in.gov.rajasthan.risl.esignergateway.dto.request;

import in.gov.rajasthan.risl.esignergateway.enums.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Incoming request payload from Merchant/ASP for the eSign redirection flow.
 *
 * Transport: multipart/form-data (confirmed as the live transport).
 *
 * FILE_TYPE discriminates what "documents" contains - PDF, XML, or
 * free-form DATA - each validated differently (see DocumentValidator).
 *
 * VERSION: numeric (e.g. "2.1", "3.2") or the literal "PREVIEW". Which
 * versions are currently live/supported is a business rule checked later
 * against configuration - not enforced structurally here.
 *
 * Document count: at least one document is always required. VERSION 3.2
 * allows up to 5; this upper bound is enforced structurally here as the
 * outer ceiling across all versions. Any tighter, version-specific count
 * is a business rule enforced later, not here.
 *
 * Note: SECRET_KEY is NOT part of this DTO - it arrives as an HTTP header,
 * not a form field, and must never be bound here.
 */
@Getter
@Setter
@ToString(exclude = {"encryptedData", "documents"})
public class MerchantRequestDTO {

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

    @NotNull(message = "FILE_TYPE is required")
    private FileType fileType;

    // MultipartFile has no @NotBlank equivalent. Per-file "not empty"
    // (file.isEmpty()) is checked in DocumentValidator, not via Bean
    // Validation annotations here.
    @NotNull(message = "documents is required")
    @NotEmpty(message = "At least one document is required")
    @Size(max = 5, message = "A maximum of 5 documents is allowed")
    private List<MultipartFile> documents;

    private String signerId;
}
