package com.example.SoftLineEC.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {

    @Value("")
    private static String filePath2;

    public static void saveFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("src/main/resources/static/images/usersimg");
        Path filePath = uploadPath.resolve(file.getOriginalFilename()).normalize();
        Files.createDirectories(uploadPath);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        filePath2 = filePath.toString().substring(25);
    }
    public static String getFilePath2(){
        return filePath2;
    }
}
