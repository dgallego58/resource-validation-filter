package co.com.bancolombia;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/file-service")
public class FileController {


    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final FileUseCase fileUseCase;

    public FileController(FileUseCase fileUseCase) {
        this.fileUseCase = fileUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> uploadAndProcess(@RequestParam("file") MultipartFile file) {
        try {
            fileUseCase.process(file.getBytes());
        } catch (IOException e) {
            log.info("FALLÓ AL CARGAR EL ARCHIVO");
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
    }
}
