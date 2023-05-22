package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import com.example.SoftLineEC.repositories.BlockRepository;
import com.example.SoftLineEC.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
/**
 * Контроллер, обрабатывающий запросы для блоков курсов.
 */
@Controller
public class BlockController {
    /**
     * Интерфейс для выполнения операций с базой данных, связанных с сущностью Course.
     */
    @Autowired
    private CourseRepository courseRepository;
    /**
     * Интерфейс для выполнения операций с базой данных, связанных с сущностью Block.
     */
    @Autowired
    private BlockRepository blockRepository;
    /**
     * Обрабатывает GET-запрос на получение списка блоков курсов.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для отображения списка блоков.
     */
    @GetMapping("/Block")
    public String Block(Model model)
    {
        Iterable<Block> block = blockRepository.findAll();
        model.addAttribute("Block", block);
        return "BlockMain";
    }
    /**
     * Обрабатывает GET-запрос на добавление нового блока курса.
     * @param Block объект блока курса.
     * @param addr объект, используемый для передачи данных в представление.
     * @return имя представления для отображения формы добавления нового блока.
     */
    @GetMapping("/BlockAdd")
    public String BlockAdd(@ModelAttribute("Block") Block Block, Model addr)
    {
        Iterable<Course> nameOfCourse = courseRepository.findAll();
        addr.addAttribute("nameOfCourse",nameOfCourse);
        return "BlockAdd";
    }
    /**
     * Обрабатывает POST-запрос на добавление нового блока курса.
     * @param Block объект блока курса.
     * @param bindingResult объект, используемый для валидации данных формы.
     * @param nameOfCourse имя курса, к которому относится блок.
     * @param addr объект, используемый для передачи данных в представление.
     * @return имя представления для отображения списка блоков.
     */
    @PostMapping("/BlockAdd")
    public String BlockAddAdd(@ModelAttribute("Block") @Valid Block Block, BindingResult bindingResult,
                               @RequestParam String nameOfCourse, Model addr)
    {
        if (bindingResult.hasErrors()) {
            Iterable<Course> Course = courseRepository.findAll();
            addr.addAttribute("nameOfCourse",Course);
            return "BlockAdd";
        }
        Block.setCourseID(courseRepository.findByCategoriesOfStudentsOrNameOfCourse(nameOfCourse,nameOfCourse));
        blockRepository.save(Block);
        return "BlockMain";
    }
    /**
     * Обрабатывает GET-запрос на редактирование существующего блока курса.
     * @param idBlock идентификатор блока курса.
     * @param model объект, используемый для передачи данных в представление.
     * @return имя представления для отображения формы редактирования блока.
     */
    @GetMapping("/Block/{idBlock}/edit")
    public String BlockEdit(@PathVariable(value = "idBlock") long idBlock, Model model)
    {
        if(!blockRepository.existsById(idBlock)){
            return "redirect:/Block";
        }
        Optional<Block> block = blockRepository.findById(idBlock);
        ArrayList<Block> res = new ArrayList<>();
        block.ifPresent(res::add);
        model.addAttribute("Block", res);
        Iterable<Course> course = courseRepository.findAll();
        model.addAttribute("Course",course);
        return "BlockEdit";
    }
    /**
     * Обрабатывает POST-запрос на обновление существующего блока курса.
     * @param idBlock идентификатор блока курса.
     * @param block объект блока курса.
     * @param bindingResult объект, используемый для валидации данных формы.
     * @param nameOfCourse имя курса, к которому относится блок.
     * @return перенаправление на страницу списка блоков.
     */
    @PostMapping("/Block/{idBlock}/edit")
    public String BlockUpdate(@PathVariable("idBlock")long idBlock,
                              @Valid Block block, BindingResult bindingResult, @RequestParam String nameOfCourse)
    {
        if (bindingResult.hasErrors())
            return "BlockEdit";
        block.setCourseID(courseRepository.findByCategoriesOfStudentsOrNameOfCourse(nameOfCourse,nameOfCourse));
        blockRepository.save(block);
        return "redirect:/Block";
    }
    /**
     * Обрабатывает GET-запрос на удаление существующего блока курса.
     * @param idBlock идентификатор блока курса.
     * @param model объект, используемый для передачи данных в представление.
     * @return перенаправление на страницу списка блоков.
     */
    @GetMapping("/Block/{idBlock}/remove")
    public String BlockRemove(@PathVariable("idBlock") long idBlock, Model model)
    {
        Block block = blockRepository.findById(idBlock).orElseThrow();
        blockRepository.delete(block);
        return "redirect:/Block";
    }
}
