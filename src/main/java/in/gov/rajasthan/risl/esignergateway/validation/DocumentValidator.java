package in.gov.rajasthan.risl.esignergateway.validation;

import in.gov.rajasthan.risl.esignergateway.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentValidator {
    void validate(FileType fileType, List<MultipartFile> documents);
}
