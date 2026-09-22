package in.gov.rajasthan.risl.esignergateway.validation.impl;

import in.gov.rajasthan.risl.esignergateway.enums.FileType;
import in.gov.rajasthan.risl.esignergateway.exception.InvalidDocumentException;
import in.gov.rajasthan.risl.esignergateway.validation.DocumentValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.List;

/**
 * Structural, format-level validation of uploaded documents against the
 * declared FILE_TYPE. One method per type rather than one large
 * conditional, per section 13's modularity requirement.
 */
@Component
public class DocumentValidatorImpl implements DocumentValidator {

    private static final String PDF_CONTENT_TYPE = "application/pdf";
    private static final String PDF_EXTENSION = ".pdf";

    @Override
    public void validate(FileType fileType, List<MultipartFile> documents) {
        switch (fileType) {
            case PDF -> documents.forEach(this::validatePdf);
            case XML -> documents.forEach(this::validateXml);
            case DATA -> { /* free-form: no content-format constraint beyond non-empty, already enforced structurally */ }
        }
    }

    private void validatePdf(MultipartFile file) {
        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();
        boolean looksLikePdf = PDF_CONTENT_TYPE.equalsIgnoreCase(contentType)
                || (filename != null && filename.toLowerCase().endsWith(PDF_EXTENSION));

        if (!looksLikePdf) {
            throw new InvalidDocumentException(
                    "FILE_TYPE=PDF but uploaded content does not appear to be a PDF: " + filename);
        }
    }

    private void validateXml(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Blocks DOCTYPE declarations - prevents XXE (XML External
            // Entity) attacks via a malicious DTD in an uploaded "XML" file.
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(inputStream);
        } catch (Exception ex) {
            throw new InvalidDocumentException(
                    "FILE_TYPE=XML but content is not well-formed XML: " + file.getOriginalFilename());
        }
    }
}
