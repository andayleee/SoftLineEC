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
/**
 * Контроллер для загрузки и сохранения файлов изображений.
 */
@Controller
public class FileUploadController {
    /**
     * Репозиторий фото.
     */
    @Autowired
    private PhotoRepository photoRepository;
    /**
     * Обрабатывает GET-запрос на страницу загрузки файла.
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления, отображающего страницу загрузки файла
     */
    @GetMapping("/upload")
    public String savePage(Model model) {return "SaveFile";}
    /**
     * Обрабатывает POST-запрос на загрузку файла.
     * @param file объект MultipartFile, представляющий загруженный файл
     * @param photo объект Photo, в который будет сохранена информация о файле
     * @return объект ResponseEntity с сообщением об успешной загрузке файла
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, Photo photo) throws IOException {
        FileUploadService.saveFile(file);
        photo.setPhotoPath(FileUploadService.getFilePath2());
        photoRepository.save(photo);
        return ResponseEntity.ok("File uploaded successfully!");
    }
}
