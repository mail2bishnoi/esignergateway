package in.gov.rajasthan.risl.esignergateway.util;

import in.gov.rajasthan.risl.esignergateway.exception.DocumentHashException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HexFormat;

/**
 * Computes the SHA-256 hash of a document, hex-encoded, for the eSign
 * request XML's InputHash element (hashAlgorithm="SHA256").
 */
@Component
public class DocumentHasher {

    public String sha256Hex(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (Exception ex) {
            throw new DocumentHashException(
                    "Failed to compute SHA-256 hash for document: " + file.getOriginalFilename(), ex);
        }
    }
}