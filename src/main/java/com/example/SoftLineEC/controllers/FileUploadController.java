package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Photo;
import com.example.SoftLineEC.repositories.PhotoRepository;
import com.example.SoftLineEC.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {

    @Autowired
    private PhotoRepository photoRepository;
    @GetMapping("/upload")
    public String savePage(Model model) {return "SaveFile";}

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, Photo photo) throws IOException {
        FileUploadService.saveFile(file);
        photo.setPhotoPath(FileUploadService.getFilePath2());
        photoRepository.save(photo);
        return ResponseEntity.ok("File uploaded successfully!");
    }
}
