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

@Controller
public class CreateCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private FormOfEducationRepository formOfEducationRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private  AnswerOptionsRepository answerOptionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

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

    @GetMapping("/CreatedCourses")
    public String CreatedCoursesPage(@ModelAttribute("Course") Course Course, Model addr, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findUserByUsername(username);
        Iterable<Course> course = courseRepository.findCoursesByUserID(user);
        addr.addAttribute("Course", course);
        return "CreatedCourses";
    }

    @GetMapping("/course")
    public String getCoursePage(@RequestParam("id") Long courseId, HttpSession session) {
        session.setAttribute("idCourse", courseId);
        return "redirect:/CreateNewCourse/details";
    }

    @RequestMapping(value = "/check-course-name", method = RequestMethod.GET)
    @ResponseBody
    public String CheckCourseName(HttpSession session) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        String content = course.get().getNameOfCourse();
        return content;
    }

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

    @GetMapping("/CreateNewCourse/details")
    public String CourseEdit(HttpSession session, Model model) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        if (!courseRepository.existsById(idCourse)) {
            return "redirect:/CreateNewCourse";
        }
        return "CourseDetails";
    }

    @GetMapping("/CreateNewCourse/details/edit")
    public String CourseEditt(HttpSession session, Model model) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        if (!courseRepository.existsById(idCourse)) {
            return "redirect:/CreateNewCourse/details";
        }
        return "CourseDetailsEdit";
    }

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

    @GetMapping("/blockAddSample")
    public String getBlock(@RequestParam("num") String num, Model model) {
        model.addAttribute("num", num);
        return "blockAddSample :: copy";
    }

    @GetMapping("/lectureAddSample")
    public String getLecture(@RequestParam("num") String num, @RequestParam("nameOfLectur") String nameOfLectur, Model model) {
        model.addAttribute("num", num);
        model.addAttribute("nameOfLectur", nameOfLectur);
        return "lectureAddSample :: copy1";
    }

    @GetMapping("/questionAddSample")
    public String getQuestion(@RequestParam("num") String num, Model model) {
        model.addAttribute("num", num);
        return "questionAddSample :: copy";
    }

    @GetMapping("/answerOptionsAddSample")
    public String getAnswerOptions(@RequestParam("num") String num, @RequestParam("nameOfLectur") String nameOfLectur, Model model) {
        model.addAttribute("num", num);
        model.addAttribute("nameOfLectur", nameOfLectur);
        return "answerOptionsAddSample :: copy1";
    }

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

    @RequestMapping(value = "/check-blocks", method = RequestMethod.POST)
    @ResponseBody
    public List<Block> checkBlocks(HttpSession session)
    {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        List<Block> blocks = blockRepository.findBlocksByCourseID(course.get());

        return blocks;
    }

    @RequestMapping(value = "/check-lectures/{id}", method = RequestMethod.POST)
    @ResponseBody
    public List<Lecture> checkLectures(@PathVariable("id") Long idBlock) {
        Optional<Block> block = blockRepository.findById(idBlock);
        List<Lecture> lectures = lectureRepository.findLecturesByBlockID(block.get());

        return lectures;
    }

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

    @RequestMapping(value = "/CreateNewCourse/details/Block/Edit", method = RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsEditt(@RequestParam("id") int id, Model model, HttpSession session) {
        long idBlock = id;
        session.setAttribute("idBlock", idBlock);
        model.addAttribute("id", id);
        return id;
    }

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

    @RequestMapping(value = "/CreateNewCourse/details/Lecture/Edit", method = RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsLectureEditt(@RequestParam("id") int id, Model model, HttpSession session) {
        long idLecture = id;
        session.setAttribute("idLecture", idLecture);
        model.addAttribute("id", id);
        return id;
    }

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

    @RequestMapping(value = "/CreateNewCourse/details/Lecture/Edit/Content", method = RequestMethod.POST)
    @ResponseBody
    public int CourseDetailsLectureContentEditt(@RequestParam("id") int id, Model model, HttpSession session) {
        long idLecture = id;
        session.setAttribute("idLecture", idLecture);
        model.addAttribute("id", id);
        return id;
    }

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

    @RequestMapping(value = "/check-lectures-details", method = RequestMethod.POST)
    @ResponseBody
    public List<Photo> checkLectures(HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        List<Photo> photos = photoRepository.findPhotosByLectureID(lecture.get());
        return photos;
    }

    @RequestMapping(value = "/check-lectures-details-text", method = RequestMethod.POST)
    @ResponseBody
    public String checkLecturesText(HttpSession session) {
        Long idLecture = (Long) session.getAttribute("idLecture");
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        String content = lecture.get().getContent();
        return content;
    }
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