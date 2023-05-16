package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.*;
import com.example.SoftLineEC.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
public class PassingTheCourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersCoursesRepository usersCoursesRepository;
    @Autowired
    private AnswerOptionsRepository answerOptionsRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;

    @GetMapping("/passingTheCourse")
    public String mainPage(HttpSession session, Model model) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        Course course1 = courseRepository.findCourseByIdCourse(idCourse);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Course", res);
        List<Block> block = blockRepository.findBlocksByCourseID(course1);
        model.addAttribute("Block", block);
        return "PassingTheCourse";
    }

    @RequestMapping(value = "/check-lectures-photos", method = RequestMethod.POST)
    @ResponseBody
    public List<Photo> checkLectures(@RequestParam("lectureId") Long idLecture, HttpSession session) {
        Optional<Lecture> lecture = lectureRepository.findById(idLecture);
        List<Photo> photos = photoRepository.findPhotosByLectureID(lecture.get());
        return photos;
    }

    @RequestMapping(value = "/check-lectures-text", method = RequestMethod.POST)
    @ResponseBody
    public String checkLecturesText(@RequestParam("lectureId") Long idLecture, HttpSession session) {
        session.setAttribute("idLecture", idLecture);
        try {
            Optional<Lecture> lecture = lectureRepository.findById(idLecture);
            String content = lecture.get().getContent();
            User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
            Course course = courseRepository.findCourseByIdCourse((Long) session.getAttribute("idCourse"));
            UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
            String passedLectures = usersCourses.getPassedLectures();
            if (passedLectures!=null){
                List<String> lecturesList = Arrays.asList(passedLectures.trim().split("\\s+"));
                if (!lecturesList.contains(idLecture.toString())) {
                    usersCourses.setPassedLectures(idLecture + " " + usersCourses.getPassedLectures());
                    usersCoursesRepository.save(usersCourses);
                }
            }else {
                usersCourses.setPassedLectures(idLecture.toString());
                usersCoursesRepository.save(usersCourses);
            }
            return content;
        } catch (NullPointerException e){
            return null;
        }
    }
    @RequestMapping(value = "/check-checkbox", method = RequestMethod.POST)
    @ResponseBody
    public List<String> checkCheckbox(HttpSession session) {
        try {
            User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
            Course course = courseRepository.findCourseByIdCourse((Long) session.getAttribute("idCourse"));
            UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
            String passedLectures = usersCourses.getPassedLectures();
            List<String> lecturesList = Arrays.asList(passedLectures.trim().split("\\s+"));
            return lecturesList;
        }catch (NullPointerException e){
            return null;
        }
    }

    @RequestMapping(value = "/handlePassTheTestClick", method = RequestMethod.POST)
    @ResponseBody
    public int handleProfileInfoEdit(@RequestBody Map<String, Object> data, HttpSession session) {
        List<String> answers = (List<String>) data.get("answers");
        List<String> allAnswers = (List<String>) data.get("allAnswers");
        int score = 0;
        if (answers.size() == 0) {
            return score;
        }
        Set<Question> answeredQuestions = new HashSet<>();
        for (String answer : allAnswers) {
            try {
                Long id = Long.valueOf(answer);
                Optional<AnswerOptions> answerOptions = answerOptionsRepository.findById(id);
                Question question = questionRepository.findByTenants(answerOptions);
                Test test = testRepository.findByTenants(question);
                session.setAttribute("idTest", test.getIdTest());
                if (answeredQuestions.contains(question)) {
                    continue;
                }
                answeredQuestions.add(question);
                List<AnswerOptions> answers1 = answerOptionsRepository.findAnswerOptionsByQuestionID(question);
                int maxCorrectAnswers = 0;
                int countCorrectAnswers = 0;
                for (AnswerOptions answer1 : answers1) {
                    if (answer1.isValid()) {
                        maxCorrectAnswers++;
                    }
                }
                boolean allSelectedAnswersValid = true;
                for (String selectedAnswerId : answers) {
                    Long selectedId = Long.valueOf(selectedAnswerId);
                    Optional<AnswerOptions> selectedAnswerOptions = answerOptionsRepository.findById(selectedId);
                    if (selectedAnswerOptions.isPresent() && !selectedAnswerOptions.get().isValid()) {
                        allSelectedAnswersValid = false;
                        break;
                    } else if (selectedAnswerOptions.isPresent() && selectedAnswerOptions.get().isValid()) {
                        countCorrectAnswers++;
                    }
                }
                if (allSelectedAnswersValid && countCorrectAnswers == maxCorrectAnswers) {
                    score = score+ question.getScore();
                }
            }catch (NullPointerException e){

            }
        }
        return score;
    }

    @RequestMapping(value = "/test-results-save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> handleTestResultsSaveRequest(@RequestBody String count, HttpSession session) {
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        Course course = courseRepository.findCourseByIdCourse((Long) session.getAttribute("idCourse"));
        UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
        usersCourses.setTestResults("("+session.getAttribute("idTest") + "-" + count.replaceAll("\"", "")+")");
        usersCoursesRepository.save(usersCourses);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/check-correct-answers", method = RequestMethod.POST)
    @ResponseBody
    public Collection<Question> handleProfileInfoEdit(HttpSession session) {
        Long idTest = (Long) session.getAttribute("idTest");
        Test test = testRepository.findTestByIdTest(idTest);
        return test.getTenants();
    }

    @RequestMapping(value = "/check-lectures-passing-test", method = RequestMethod.POST)
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

    @RequestMapping(value = "/check-test-success", method = RequestMethod.POST)
    @ResponseBody
    public String[] handleTestSuccess(HttpSession session) {
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        Course course = courseRepository.findCourseByIdCourse((Long) session.getAttribute("idCourse"));
        UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
        if (usersCourses.getTestResults()!=null){
            return usersCourses.getTestResults().split("\\s*-|\\s*\\(|\\s*\\)");
        }
        return null;
    }

    @RequestMapping(value = "/set-course-end", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleProfileAuthInfoEdit(HttpSession session) {
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        Course course = courseRepository.findCourseByIdCourse((Long) session.getAttribute("idCourse"));
        UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
        usersCourses.setCompleteness(true);
        usersCoursesRepository.save(usersCourses);
        return ResponseEntity.ok("Данные успешно сохранены");
    }
}
