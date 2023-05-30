package co.com.dgallego58.service;

import co.com.dgallego58.bd.DataBaseEmulator;
import co.com.dgallego58.user.User;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ValidationFilterService {

    private static final Logger log = LoggerFactory.getLogger(ValidationFilterService.class);
    private final DataBaseEmulator myRepo;

    private ValidationFilterService() {
        this.myRepo = DataBaseEmulator.INSTANCE;
    }

    public static ValidationFilterService createDefault() {
        return new ValidationFilterService();
    }

    public User findBy(UUID id) {
        return myRepo.findById(id);
    }

    public User save(User user) {
        return myRepo.save(user);
    }

    public void checkFile(File file) {
        //FilenameUtils -> apache commons
        String extension = FilenameUtils.getExtension(file.getName());
        FileType fileType = FileType.fromExtension(extension);
        //signature check
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] signature = new byte[fileType.bytesToCheck()];
            int bytesRead = fis.read(signature, 0, fileType.bytesToCheck());
            log.info("Bytes Read: {}", bytesRead);
            String signatureAsHex = StringUtil.toHex(signature);
            log.info("Signature bytes as Hex: {}", signatureAsHex);

            String isoSignature = new String(signature, StandardCharsets.ISO_8859_1);
            log.info("ISO 8859-1 Signature: {}", isoSignature);

            boolean isTrustyFile = fileType.getHexSignature().compareToIgnoreCase(signatureAsHex) == 0;
            log.info("The file is trusty?: {}", isTrustyFile);
            if (!isTrustyFile) {
                throw new InvalidFileException("The file is compromised and does not match its signature bytes");
            }
        } catch (IOException e) {
            throw new UndefinedFileException(e);
        }

    }

    static class InvalidFileException extends RuntimeException {
        public InvalidFileException(String message) {
            super(message);
        }
    }

    static class UndefinedFileException extends RuntimeException {
        public UndefinedFileException(Throwable cause) {
            super(cause);
        }
    }


}
