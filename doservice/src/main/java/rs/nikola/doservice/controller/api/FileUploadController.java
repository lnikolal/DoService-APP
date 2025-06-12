package rs.nikola.doservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.nikola.doservice.service.FileStorageService;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {


    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // Upload endpoint
    @PostMapping("/upload")
    public UploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String url = fileStorageService.store(file);
        return new UploadResponse(url, "File uploaded successfully!");
    }

    // Download endpoint (ako baš želiš preko API, ali nije neophodno za slike)
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource file = fileStorageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    // DTO za odgovor
    public static class UploadResponse {
        private String url;
        private String message;

        public UploadResponse(String url, String message) {
            this.url = url;
            this.message = message;
        }
        public String getUrl() { return url; }
        public String getMessage() { return message; }
        public void setUrl(String url) { this.url = url; }
        public void setMessage(String message) { this.message = message; }
    }
}
