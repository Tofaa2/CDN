package me.tofaa.cdn.services;

import me.tofaa.cdn.exceptions.FileDownloadException;
import me.tofaa.cdn.exceptions.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {

    private static final File STORAGE_FILE = Path.of("content").toFile();

    public File getFile(String fileName) throws FileDownloadException {
        if (fileName == null) throw new FileDownloadException("Did not supply file name");
        var file = new File(STORAGE_FILE, fileName);
        if (!isPermittedFile(file)) {
            throw new FileUploadException("Attempted to exploit the pathing via downloaded file.");
        }
        if (!file.exists()) {
            throw new FileUploadException("Attempted to get a nonexistent file (" + file.getName() + ")");
        }
        return file;
    }

    public void saveFile(MultipartFile file) throws FileUploadException {
        if (file == null) throw new FileUploadException("Attempted to save null file");

        long now = System.currentTimeMillis();

        var target = new File(STORAGE_FILE, file.getOriginalFilename());
        if (!isPermittedFile(target)) {
            throw new FileUploadException("Attempted to exploit the pathing via uploaded file.");
        }
        var modified = new File(STORAGE_FILE, now + "_cdn_" + file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), modified.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new FileUploadException("Failed to copy uploaded file contents", e);
        }
    }

    private boolean isPermittedFile(File file) {
        return Objects.equals(file.getParentFile(), STORAGE_FILE);
    }

}
