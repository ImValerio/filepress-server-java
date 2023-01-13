package com.example.serverjava.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static com.example.serverjava.util.SharedUtil.sanitizeFileName;

public class FileUploadUtil {


    public static Path saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get("uploads");
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileCode = String.valueOf(UUID.randomUUID());
        InputStream inputStream = multipartFile.getInputStream();
        fileName = sanitizeFileName(fileName);
        Path filePath = uploadPath.resolve(fileCode+"_"+fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath;
    }
}
