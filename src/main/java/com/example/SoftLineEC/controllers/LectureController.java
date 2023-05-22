package com.example.SoftLineEC.controllers;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.Photo;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.LectureRepository;
import com.example.SoftLineEC.repositories.PhotoRepository;
import com.example.SoftLineEC.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
/**
 * Контроллер для управления лекциями.
 */
@Controller
public class LectureController {
    /**
     * Репозиторий блоков.
     */
    @Autowired
    private BlockRepository blockRepository;
    /**
     * Репозиторий лекций.
     */
    @Autowired
    private LectureRepository lectureRepository;
    /**
     * Репозиторий фото.
     */
    @Autowired
    private PhotoRepository photoRepository;
    /**
     * Обрабатывает GET-запрос на получение страницы со всеми лекциями.
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления, отображающего страницу со всеми лекциями
     */
    @GetMapping("/Lecture")
    public String Lecture(Model model)
    {
        Iterable<Lecture> lecture = lectureRepository.findAll();
        model.addAttribute("Lecture", lecture);
        return "LectureMain";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы для добавления новой лекции.
     * @param Lecture объект Lecture, используемый для сохранения данных о новой лекции
     * @param addr объект Model, используемый для передачи данных в представление
     * @return имя представления, отображающего страницу для добавления новой лекции
     */
    @GetMapping("/LectureAdd")
    public String LectureAdd(@ModelAttribute("Lecture") Lecture Lecture, Model addr)
    {
        Iterable<Block> nameOfBlock = blockRepository.findAll();
        addr.addAttribute("nameOfBlock",nameOfBlock);
        return "LectureAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление новой лекции.
     * @param Lecture объект Lecture, содержащий данные о новой лекции
     * @param bindingResult объект BindingResult, содержащий ошибки валидации
     * @param file MultipartFile, содержащий фотографию для лекции
     * @param photo объект Photo, используемый для сохранения данных о фотографии
     * @param nameOfBlock название блока, в который добавляется новая лекция
     * @param addr объект Model, используемый для передачи данных в представление
     * @return имя представления со списком всех лекций в случае успешного добавления, иначе - страница для добавления новой лекции
     */
    @PostMapping("/LectureAdd")
    public String LectureAddAdd(@ModelAttribute("Lecture") @Valid Lecture Lecture, BindingResult bindingResult,
                                @RequestParam("file") MultipartFile file, Photo photo,
                                @RequestParam String nameOfBlock, Model addr) throws IOException
    {
        if (bindingResult.hasErrors()) {
            Iterable<Block> nameOfBlockk = blockRepository.findAll();
            addr.addAttribute("nameOfBlock",nameOfBlockk);
            return "LectureAdd";
        }
        Lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(Lecture);
        FileUploadService.saveFile(file);
        photo.setPhotoPath(FileUploadService.getFilePath2());
        photo.setLectureID(lectureRepository.findByNameOfLecture(Lecture.getNameOfLecture()));
        photoRepository.save(photo);
        return "redirect:/Lecture";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы для редактирования лекции.
     * @param idLecture идентификатор лекции, которую необходимо отредактировать
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления, отображающего страницу для редактирования лекции
     */
    @GetMapping("/Lecture/{idLecture}/edit")
    public String LectureEdit(@PathVariable(value = "idLecture") long idLecture, Model model)
    {
        if(!lectureRepository.existsById(idLecture)){
            return "redirect:/Lecture";
        }
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        ArrayList<Lecture> res = new ArrayList<>();
        lecture.ifPresent(res::add);
        model.addAttribute("Lecture", res);
        Iterable<Block> block = blockRepository.findAll();
        model.addAttribute("Block",block);
        return "LectureEdit";
    }
    /**
     * Обрабатывает POST-запрос на редактирование лекции.
     * @param idLecture идентификатор лекции, которую необходимо отредактировать
     * @param lecture объект Lecture, содержащий данные о лекции после редактирования
     * @param bindingResult объект BindingResult, содержащий ошибки валидации
     * @param file MultipartFile, содержащий фотографию для лекции
     * @param photo объект Photo, используемый для сохранения данных о фотографии
     * @param nameOfBlock название блока, к которому принадлежит лекция
     * @return имя представления со списком всех лекций в случае успешного редактирования, иначе - страница для редактирования лекции
     */
    @PostMapping("/Lecture/{idLecture}/edit")
    public String LectureUpdate(@PathVariable("idLecture")long idLecture,
                                @Valid Lecture lecture, BindingResult bindingResult,
                                @RequestParam("file") MultipartFile file, Photo photo,
                                @RequestParam String nameOfBlock) throws IOException {
        if (bindingResult.hasErrors())
            return "LectureEdit";
        lecture.setBlockID(blockRepository.findByNameOfBlockOrDescriptionOrDuration(nameOfBlock,nameOfBlock,nameOfBlock));
        lectureRepository.save(lecture);
        return "redirect:/Lecture";
    }
    /**
     * Обрабатывает GET-запрос на удаление лекции.
     * @param idLecture идентификатор лекции, которую необходимо удалить
     * @param model объект Model, используемый для передачи данных в представление
     * @return имя представления со списком всех лекций после удаления лекции
     */
    @GetMapping("/Lecture/{idLecture}/remove")
    public String LectureRemove(@PathVariable("idLecture") long idLecture, Model model)
    {
        Lecture lecture = lectureRepository.findById(idLecture).orElseThrow();
        lectureRepository.delete(lecture);
        return "redirect:/Lecture";
    }
}
