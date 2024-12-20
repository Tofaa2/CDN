package me.tofaa.cdn.controllers;

import me.tofaa.cdn.exceptions.FileDownloadException;
import me.tofaa.cdn.exceptions.FileUploadException;
import me.tofaa.cdn.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileManagerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManagerController.class);

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public boolean uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            fileService.saveFile(file);
            LOGGER.info("Saved file {}", file.getOriginalFilename());
            return true;
        }
        catch (FileUploadException e) {
            LOGGER.error("Failed to upload file", e);
            return false;
        }
    }

    @GetMapping("downloadMultipart")
    public ResponseEntity<Resource> downloadFileMp(@RequestParam("file") String fileName) {
        try {
            var file = fileService.getFile(fileName);
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(file));
        }
        catch (FileDownloadException e) {
            LOGGER.error("Error getting file", e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("file") String fileName) {
        try {
            var file = fileService.getFile(fileName);
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(Files.newInputStream(file.toPath())));
        }
        catch (FileDownloadException | IOException e) {
            LOGGER.error("Error getting file", e);
            return ResponseEntity.notFound().build();
        }
    }

}
