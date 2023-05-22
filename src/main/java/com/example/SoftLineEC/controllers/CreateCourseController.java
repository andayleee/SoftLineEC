package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.*;
import com.example.SoftLineEC.repositories.*;
import com.example.SoftLineEC.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
/**
 * Контроллер, обрабатывающий запросы для создания новых курсов.
 */
@Controller
public class CreateCourseController {
    /**
     * Репозиторий курсов.
     */
    @Autowired
    private CourseRepository courseRepository;
    /**
     * Репозиторий типов курсов.
     */
    @Autowired
    private CourseTypeRepository courseTypeRepository;
    /**
     * Репозиторий форм обучения.
     */
    @Autowired
    private FormOfEducationRepository formOfEducationRepository;
    /**
     * Репозиторий блоков курса.
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
     * Репозиторий тестов.
     */
    @Autowired
    private TestRepository testRepository;
    /**
     * Репозиторий вопросов.
     */
    @Autowired
    private QuestionRepository questionRepository;
    /**
     * Репозиторий вариантов ответов.
     */
    @Autowired
    private  AnswerOptionsRepository answerOptionsRepository;
    /**
     * Репозиторий пользователей.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Репозиторий тем курсов.
     */
    @Autowired
    private ThemeRepository themeRepository;
    /**
     * Обрабатывает GET-запрос на получение страницы создания нового курса.
     * @param Course объект курса.
     * @param addr объект, используемый для передачи данных в представление.
     * @return имя представления для создания нового курса.
     */
    @GetMapping("/CreateNewCourse")
    public String CreateNewCoursePage(@ModelAttribute("Course") Course Course, Model addr) {
        Iterable<CourseType> courseType = courseTypeRepository.findAll();
        addr.addAttribute("CourseTypes", courseType);
        Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
        addr.addAttribute("FormOfEducations", formOfEducations);
        Iterable<Theme> themes = themeRepository.findAll();
        addr.addAttribute("Theme", themes);
        return "CreateNewCourse";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы списка созданных курсов.
     * @param Course объект курса.
     * @param addr объект, используемый для передачи данных в представление.
     * @param authentication информация об аутентификации пользователя.
     * @return имя представления для списка созданных курсов.
     */
    @GetMapping("/CreatedCourses")
    public String CreatedCoursesPage(@ModelAttribute("Course") Course Course, Model addr, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findUserByUsername(username);
        Iterable<Course> course = courseRepository.findCoursesByUserID(user);
        addr.addAttribute("Course", course);
        return "CreatedCourses";
    }
    /**
     * Обрабатывает GET-запрос на получение страницы курса.
     * @param courseId идентификатор курса.
     * @param session объект, представляющий сессию пользователя.
     * @return перенаправление на страницу деталей курса.
     */
    @GetMapping("/course")
    public String getCoursePage(@RequestParam("id") Long courseId, HttpSession session) {
        session.setAttribute("idCourse", courseId);
        return "redirect:/CreateNewCourse/details";
    }
    /**
     * Обрабатывает GET-запрос на проверку имени курса.
     * @param session объект, представляющий сессию пользователя.
     * @return имя курса.
     */
    @RequestMapping(value = "/check-course-name", method = RequestMethod.GET)
    @ResponseBody
    public String CheckCourseName(HttpSession session) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        String content = course.get().getNameOfCourse();
        return content;
    }
    /**
     * Создает новый курс и сохраняет его в базе данных.
     * @param course объект Course, содержащий информацию о создаваемом курсе
     * @param bindingResult результат проверки валидации объекта курса
     * @param nameOfCourseType название типа курса, связанного с курсом
     * @param typeOfEducation тип обучения, связанный с курсом
     * @param nameOfTheme название темы, связанной с курсом
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @param addr объект Model, используемый для сохранения и получения атрибутов модели
     * @param authentication объект Authentication, представляющий текущий статус аутентификации пользователя
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @PostMapping("/CreateNewCourse")
    public String CoursesAdd(@ModelAttribute("Course") @Valid Course course, BindingResult bindingResult,
                             @RequestParam String nameOfCourseType, @RequestParam String typeOfEducation, @RequestParam String nameOfTheme,
                             HttpSession session, Model addr, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            Iterable<CourseType> courseType = courseTypeRepository.findAll();
            addr.addAttribute("CourseTypes", courseType);
            Iterable<FormOfEducation> formOfEducations = formOfEducationRepository.findAll();
            addr.addAttribute("FormOfEducations", formOfEducations);
            Iterable<Theme> themes = themeRepository.findAll();
            addr.addAttribute("Theme", themes);
            return "CreateNewCourse";
        }
        course.setCourseTypeID(courseTypeRepository.findByNameOfCourseType(nameOfCourseType));
        course.setFormOfEducationID(formOfEducationRepository.findByTypeOfEducation(typeOfEducation));
        course.setThemeID(themeRepository.findThemeByNameOfTheme(nameOfTheme));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findUserByUsername(username);
        long idUser = user.getId();
        session.setAttribute("idUser", idUser);
        course.setUserID(user);
        LocalDate localDate = LocalDate.now();
        Date date = Date.valueOf(localDate);
        course.setDateOfCreation(date);
        courseRepository.save(course);
        long idCourse = course.getIdCourse();
        session.setAttribute("idCourse", idCourse);
        return "redirect:/CreateNewCourse/details";
    }
    /**
     * Отображает страницу деталей созданного курса.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/CreateNewCourse/details")
    public String CourseEdit(HttpSession session, Model model) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        if (!courseRepository.existsById(idCourse)) {
            return "redirect:/CreateNewCourse";
        }
        return "CourseDetails";
    }
    /**
     * Отображает страницу для редактирования деталей созданного курса.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/CreateNewCourse/details/edit")
    public String CourseEditt(HttpSession session, Model model) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        if (!courseRepository.existsById(idCourse)) {
            return "redirect:/CreateNewCourse/details";
        }
        return "CourseDetailsEdit";
    }
    /**
     * Отображает страницу описания созданного курса.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/CourseDescription")
    public String CourseDescriptionPage(HttpSession session, Model model) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        if (!courseRepository.existsById(idCourse)) {
            return "redirect:/CreateNewCourse/details";
        }
        Optional<Course> course = courseRepository.findById(idCourse);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Course", res);
        return "CourseDescription";
    }
    /**
     * Отображает блок для добавления образца блока на страницу создания курса.
     * @param num  параметр запроса, представляющий число
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/blockAddSample")
    public String getBlock(@RequestParam("num") String num, Model model) {
        model.addAttribute("num", num);
        return "blockAddSample :: copy";
    }
    /**
     * Отображает блок для добавления образца лекции на страницу создания курса.
     * @param num           параметр запроса, представляющий число
     * @param nameOfLectur параметр запроса, представляющий название лекции
     * @param model         объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/lectureAddSample")
    public String getLecture(@RequestParam("num") String num, @RequestParam("nameOfLectur") String nameOfLectur, Model model) {
        model.addAttribute("num", num);
        model.addAttribute("nameOfLectur", nameOfLectur);
        return "lectureAddSample :: copy1";
    }
    /**
     * Отображает блок для добавления образца вопроса на страницу создания курса.
     * @param num  параметр запроса, представляющий число
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/questionAddSample")
    public String getQuestion(@RequestParam("num") String num, Model model) {
        model.addAttribute("num", num);
        return "questionAddSample :: copy";
    }
    /**
     * Возвращает имя представления answerOptionsAddSample :: copy1 с добавленными атрибутами num и nameOfLectur
     * @param num порядковый номер лекции
     * @param nameOfLectur название лекции
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/answerOptionsAddSample")
    public String getAnswerOptions(@RequestParam("num") String num, @RequestParam("nameOfLectur") String nameOfLectur, Model model) {
        model.addAttribute("num", num);
        model.addAttribute("nameOfLectur", nameOfLectur);
        return "answerOptionsAddSample :: copy1";
    }
    /**
     * Обрабатывает запрос на добавление нового блока, полученный в формате JSON, и сохраняет его в базе данных.
     * @param data данные, полученные в формате JSON
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return строку "OK", если операция выполнена успешно
     */
    @RequestMapping(value = "/blockAddSample", method = RequestMethod.POST)
    @ResponseBody
    public String handleBlockAddSample(@RequestBody Map<String, Object> data, HttpSession session) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        List<String> nameOfBlock = (List<String>) data.get("nameOfBlock");
        List<String> description = (List<String>) data.get("description");
        List<String> duration = (List<String>) data.get("duration");
        List<String> nameOfLecture = (List<String>) data.get("nameOfLecture");
        List<String> blockNum = (List<String>) data.get("blockNum");
        List<String> blockTrueNum = (List<String>) data.get("blockTrueNum");
        for (int i = 0; i < nameOfBlock.size(); i++) {
            Block block = new Block();
            Optional<Course> course = courseRepository.findById(idCourse);
            block.setNameOfBlock(nameOfBlock.get(i));
            block.setDescription(description.get(i));
            block.setDuration(duration.get(i));
            String blockTrueNum1 = blockTrueNum.get(i);
            block.setCourseID(course.get());
            blockRepository.save(block);
            for (int j = 0; j < nameOfLecture.size(); j++) {
                Lecture lecture = new Lecture();
                Optional<Block> block1 = blockRepository.findById(block.getIdBlock());
                String blockTrueNum2 = blockNum.get(j);
                if (blockTrueNum1.equals(blockTrueNum2)) {
                    lecture.setNameOfLecture(nameOfLecture.get(j));
                    lecture.setBlockID(block1.get());
                    lectureRepository.save(lecture);
                }
            }
        }
        return "OK";
    }
    /**
     * Обрабатывает запрос на добавление нового вопроса, полученный в формате JSON, и сохраняет его в базе данных.
     * @param data данные, полученные в формате JSON
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return строку "OK", если операция выполнена успешно
     */
    @RequestMapping(value = "/questionAddSample", method = RequestMethod.POST)
    @ResponseBody
    public String handleQuestionAddSample(@RequestBody Map<String, Object> data, HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        Lecture lecture = lectureRepository.findLectureByIdLecture(idLecture);
        List<String> nameOfTest = (List<String>) data.get("nameOfTest");
        List<String> nameOfQuestion = (List<String>) data.get("nameOfQuestion");
        List<String> score = (List<String>) data.get("score");
        List<String> nameOfAnswerOptions = (List<String>) data.get("nameOfAnswerOptions");
        List<Boolean> isValid = (List<Boolean>) data.get("isValid");
        List<String> blockNum = (List<String>) data.get("blockNum");
        List<String> blockTrueNum = (List<String>) data.get("blockTrueNum");
        Test test = new Test();
        test.setNameOfTest(nameOfTest.get(0));
        test.setLectureID(lecture);
        testRepository.save(test);
        Test test1 = testRepository.findByNameOfTest(nameOfTest.get(0));
        for (int i = 0; i < nameOfQuestion.size(); i++) {
            Question question = new Question(nameOfQuestion.get(i), Integer.parseInt(score.get(i)), test1);
            String blockTrueNum1 = blockTrueNum.get(i);
            questionRepository.save(question);
            for (int j = 0; j < nameOfAnswerOptions.size(); j++) {
                AnswerOptions answerOptions = new AnswerOptions();
                Optional<Question> question1 = questionRepository.findById(question.getIdQuestion());
                String blockTrueNum2 = blockNum.get(j);
                if (blockTrueNum1.equals(blockTrueNum2)) {
                    answerOptions.setContent(nameOfAnswerOptions.get(j));
                    if (isValid.get(j) == true) {
                        answerOptions.setValid(true);
                    }else {
                        answerOptions.setValid(false);
                    }
                    answerOptions.setQuestionID(question1.get());
                    answerOptionsRepository.save(answerOptions);
                }
            }
        }
        return "OK";
    }
    /**
     * Удаляет блок по его идентификатору.
     * @param id идентификатор теста.
     * @return объект ResponseEntity с сообщением об успешном удалении или об ошибке
     */
    @DeleteMapping("/delete-test/{id}")
    public ResponseEntity<String> deleteTest(@PathVariable Long id) {
        try {
            Test test = testRepository.findTestByIdTest(id);
            testRepository.delete(test);
            return new ResponseEntity<>("Тест успешно удален", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Тест не удален", HttpStatus.OK);
        }
    }
    /**
     * Проверяет наличие блоков для текущего курса в базе данных.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return список блоков текущего курса
     */
    @RequestMapping(value = "/check-blocks", method = RequestMethod.POST)
    @ResponseBody
    public List<Block> checkBlocks(HttpSession session)
    {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        List<Block> blocks = blockRepository.findBlocksByCourseID(course.get());

        return blocks;
    }
    /**
     * Проверяет наличие лекций для блока с указанным идентификатором в базе данных.
     * @param idBlock идентификатор блока
     * @return список лекций для указанного блока
     */
    @RequestMapping(value = "/check-lectures/{id}", method = RequestMethod.POST)
    @ResponseBody
    public List<Lecture> checkLectures(@PathVariable("id") Long idBlock) {
        Optional<Block> block = blockRepository.findById(idBlock);
        List<Lecture> lectures = lectureRepository.findLecturesByBlockID(block.get());

        return lectures;
    }
    /**
     * Удаляет блок по его идентификатору.
     * @param idBlock идентификатор блока
     * @return объект ResponseEntity с сообщением об успешном удалении или об ошибке
     */
    @DeleteMapping("/blocks/{id}")
    public ResponseEntity<?> deleteBlockById(@PathVariable("id") Long idBlock) {
        try {
            Block block = blockRepository.findById(idBlock).orElseThrow();
            blockRepository.delete(block);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Отображает страницу редактирования подробностей блока.
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/CreateNewCourse/details/Block/Edit")
    public String CourseDetailsEdit(Model model, HttpSession session) {
        Long idBlock = (Long) session.getAttribute("idBlock");
        if (!blockRepository.existsById(idBlock)) {
            return "redirect:/CreateNewCourse/details";
        }
        Optional<Block> block = blockRepository.findById(idBlock);
        ArrayList<Block> res = new ArrayList<>();
        block.ifPresent(res::add);
        model.addAttribute("Block", res);
        return "CourseDetailsBlockEdit";
    }
    /**
     * Обрабатывает запрос на изменение подробностей блока и сохраняет их в базе данных.
     * @param id идентификатор блока
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return идентификатор блока
     */
    @RequestMapping(value = "/CreateNewCourse/details/Block/Edit", method = RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsEditt(@RequestParam("id") int id, Model model, HttpSession session) {
        long idBlock = id;
        session.setAttribute("idBlock", idBlock);
        model.addAttribute("id", id);
        return id;
    }
    /**
     * Обрабатывает запрос на изменение подробностей блока и сохраняет их в базе данных.
     * @param data объект Map, содержащий данные, переданные из JavaScript-файла
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return строку "OK"
     */
    @RequestMapping(value = "/handleBlockEdit", method = RequestMethod.POST)
    @ResponseBody
    public String handleBlockEdit(@RequestBody Map<String, Object> data, HttpSession session) {
        Long idBlock = (Long) session.getAttribute("idBlock");
        Long idCourse = (Long) session.getAttribute("idCourse");
        List<String> nameOfBlock = (List<String>) data.get("nameOfBlock");
        List<String> description = (List<String>) data.get("description");
        List<String> duration = (List<String>) data.get("duration");
        for (int i = 0; i < nameOfBlock.size(); i++) {
            Block block = blockRepository.findBlockByIdBlock(idBlock);
            Optional<Course> course = courseRepository.findById(idCourse);
            block.setNameOfBlock(nameOfBlock.get(i));
            block.setDescription(description.get(i));
            block.setDuration(duration.get(i));
            block.setCourseID(course.get());
            blockRepository.save(block);
        }
        return "OK";
    }
    /**
     * Удаляет лекцию по ее идентификатору.
     * @param idLecture идентификатор лекции
     * @return объект ResponseEntity с сообщением об успешном удалении или об ошибке
     */
    @DeleteMapping("/lecture/{id}")
    public ResponseEntity<?> deleteLectureById(@PathVariable("id") Long idLecture) {
        try {
            Lecture lecture = lectureRepository.findById(idLecture).orElseThrow();
            lectureRepository.delete(lecture);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Обрабатывает запрос на изменение подробностей курса и сохраняет их в базе данных.
     * @param data объект Map, содержащий данные, переданные из JavaScript-файла
     * @return строку "OK"
     */
    @RequestMapping(value = "/handleCourseEdit", method = RequestMethod.POST)
    @ResponseBody
    public String handleCourseEdit(@RequestBody Map<String, Object> data) {
        List<String> nameOfCourse = (List<String>) data.get("nameOfCourse");
        List<String> description = (List<String>) data.get("description");
        List<String> resources = (List<String>) data.get("resources");
        List<String> goal = (List<String>) data.get("goal");
        List<String> tasks = (List<String>) data.get("tasks");
        List<String> categoriesOfStudents = (List<String>) data.get("categoriesOfStudents");
        for (int i = 0; i < nameOfCourse.size(); i++) {
            Course course = courseRepository.findCoursesByNameOfCourse(nameOfCourse.get(i));
            Optional<CourseType> courseType = courseTypeRepository.findById(course.getCourseTypeID().getIdCourseType());
            Optional<FormOfEducation> formOfEducation = formOfEducationRepository.findById(course.getFormOfEducationID().getIdFormOfEducation());
            course.setNameOfCourse(nameOfCourse.get(i));
            course.setDateOfCreation(course.getDateOfCreation());
            course.setDescription(description.get(i));
            course.setResources(resources.get(i));
            course.setGoal(goal.get(i));
            course.setTasks(tasks.get(i));
            course.setCategoriesOfStudents(categoriesOfStudents.get(i));
            course.setCourseTypeID(courseType.get());
            course.setFormOfEducationID(formOfEducation.get());

            courseRepository.save(course);
        }
        return "OK";
    }
    /**
     * Отображает страницу редактирования подробностей лекции.
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/CreateNewCourse/details/Lecture/Edit")
    public String CourseDetailsLectureEdit(Model model, HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        if (!lectureRepository.existsById(idLecture)) {
            return "redirect:/CreateNewCourse/details";
        }
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        ArrayList<Lecture> res = new ArrayList<>();
        lecture.ifPresent(res::add);
        model.addAttribute("Lecture", res);
        return "CourseDetailsLectureEdit";
    }
    /**
     * Обрабатывает запрос на получение идентификатора лекции для редактирования подробностей лекции.
     * @param id идентификатор лекции
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return идентификатор лекции
     */
    @RequestMapping(value = "/CreateNewCourse/details/Lecture/Edit", method = RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsLectureEditt(@RequestParam("id") int id, Model model, HttpSession session) {
        long idLecture = id;
        session.setAttribute("idLecture", idLecture);
        model.addAttribute("id", id);
        return id;
    }
    /**
     * Обрабатывает запрос на изменение содержания лекции и сохраняет его в базе данных.
     * @param data объект Map, содержащий данные, переданные из JavaScript-файла
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return строку "OK"
     */
    @RequestMapping(value = "/handleLectureEdit", method = RequestMethod.POST)
    @ResponseBody
    public String handleLectureEdit(@RequestBody Map<String, Object> data, HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        List<String> nameOfLecture = (List<String>) data.get("nameOfLecture");
        List<String> description = (List<String>) data.get("description");
        List<String> additionalLiterature = (List<String>) data.get("additionalLiterature");
        for (int i = 0; i < nameOfLecture.size(); i++) {
            Lecture lecture = lectureRepository.findLectureByIdLecture(idLecture);
            Block block1 = lecture.getBlockID();
            Optional<Block> block = blockRepository.findById(block1.getIdBlock());
            lecture.setNameOfLecture(nameOfLecture.get(i));
            lecture.setDescription(description.get(i));
            lecture.setAdditionalLiterature(additionalLiterature.get(i));
            lecture.setContent(lecture.getContent());
            lecture.setBlockID(block.get());
            lectureRepository.save(lecture);
        }
        return "OK";
    }
    /**
     * Отображает страницу редактирования содержания лекции.
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return имя представления (view), которое будет показано после выполнения метода
     */
    @GetMapping("/CreateNewCourse/details/Lecture/Edit/Content")
    public String CourseDetailsLectureContentEdit(Model model, HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        if (!lectureRepository.existsById(idLecture)) {
            return "redirect:/CreateNewCourse/details";
        }
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        ArrayList<Lecture> res = new ArrayList<>();
        lecture.ifPresent(res::add);
        model.addAttribute("Lecture", res);
        return "CourseDetailsLectureContentEdit";
    }
    /**
     * Обрабатывает запрос на получение идентификатора лекции для редактирования содержания лекции.
     * @param id идентификатор лекции
     * @param model объект Model, используемый для сохранения и получения атрибутов модели
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return идентификатор лекции
     */
    @RequestMapping(value = "/CreateNewCourse/details/Lecture/Edit/Content", method = RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsLectureContentEditt(@RequestParam("id") int id, Model model, HttpSession session) {
        long idLecture = id;
        session.setAttribute("idLecture", idLecture);
        model.addAttribute("id", id);
        return id;
    }
    /**
     * Обрабатывает запрос на загрузку содержимого лекции и изображений.
     * @param content содержимое лекции
     * @param files список файлов изображений
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return объект ResponseEntity с сообщением об успешной загрузке файлов
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @RequestMapping(value = "/handleLectureContentEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("content") String content,
                                                   @RequestParam("files") List<MultipartFile> files, HttpSession session) throws IOException {

        Long idLecture = (Long) session.getAttribute("idLecture");
        Lecture lecture = lectureRepository.findLectureByIdLecture(idLecture);
        Block block1 = lecture.getBlockID();
        Optional<Block> block = blockRepository.findById(block1.getIdBlock());
        lecture.setNameOfLecture(lecture.getNameOfLecture());
        lecture.setDescription(lecture.getDescription());
        lecture.setAdditionalLiterature(lecture.getAdditionalLiterature());
        lecture.setContent(content);
        lecture.setBlockID(block.get());
        lectureRepository.save(lecture);
        for (MultipartFile file : files) {
            Photo photo = new Photo();
            FileUploadService.saveFile(file);
            photo.setPhotoPath(FileUploadService.getFilePath2());
            photo.setLectureID(lectureRepository.findLectureByIdLecture(idLecture));
            photoRepository.save(photo);
        }
        return ResponseEntity.ok("File(s) uploaded successfully");
    }
    /**
     * Обрабатывает запрос на загрузку содержимого лекции без изображений.
     * @param content содержимое лекции
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return объект ResponseEntity с сообщением об успешной загрузке файла
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @RequestMapping(value = "/handleLectureContentEditWithoutPhoto", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleFileUploadWithoutPhoto(@RequestParam("content") String content, HttpSession session) throws IOException {

        Long idLecture = (Long) session.getAttribute("idLecture");
        Lecture lecture = lectureRepository.findLectureByIdLecture(idLecture);
        Block block1 = lecture.getBlockID();
        Optional<Block> block = blockRepository.findById(block1.getIdBlock());
        lecture.setNameOfLecture(lecture.getNameOfLecture());
        lecture.setDescription(lecture.getDescription());
        lecture.setAdditionalLiterature(lecture.getAdditionalLiterature());
        lecture.setContent(content);
        lecture.setBlockID(block.get());
        lectureRepository.save(lecture);
        return ResponseEntity.ok("File(s) uploaded successfully");
    }
    /**
     * Обрабатывает запрос на проверку изображений для лекции.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return список объектов Photo, которые представляют изображения, связанные с лекцией
     */
    @RequestMapping(value = "/check-lectures-details", method = RequestMethod.POST)
    @ResponseBody
    public List<Photo> checkLectures(HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        List<Photo> photos = photoRepository.findPhotosByLectureID(lecture.get());
        return photos;
    }
    /**
     * Обрабатывает запрос на проверку содержимого лекции.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return содержимое лекции
     */
    @RequestMapping(value = "/check-lectures-details-text", method = RequestMethod.POST)
    @ResponseBody
    public String checkLecturesText(HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        String content = lecture.get().getContent();
        return content;
    }
    /**
     * Обрабатывает запрос на проверку наличия тестов для лекции.
     * @param session объект HttpSession, используемый для сохранения и получения атрибутов сессии
     * @return объект Optional, содержащий объект Test, который представляет тест, связанный с лекцией
     */
    @RequestMapping(value = "/check-lectures-test", method = RequestMethod.POST)
    @ResponseBody
    public Optional<Test> checkLecturesTest(HttpSession session) {
        try {
            Long idLecture = (Long) session.getAttribute("idLecture");
            Lecture lecture1 = lectureRepository.findLectureByIdLecture(idLecture);
            Optional<Test> test = testRepository.findTestByLectureID(lecture1);
            return test;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Удаляет фотографию по заданному идентификатору.
     * @param idPhoto идентификатор фотографии
     * @return объект ResponseEntity, который сообщает об успешном удалении фотографии, либо об ошибке сервера
     */
    @DeleteMapping("/photo/{id}")
    public ResponseEntity<?> deletePhotoById(@PathVariable("id") Long idPhoto) {
        try {
            Photo photo = photoRepository.findById(idPhoto).orElseThrow();
            photoRepository.delete(photo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}