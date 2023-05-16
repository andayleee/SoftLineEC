package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.*;
import com.example.SoftLineEC.repositories.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Controller
public class MainPageController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersCoursesRepository usersCoursesRepository;
    @GetMapping("/main")
    public String mainPage(Authentication authentication, HttpSession session) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepository.findUserByUsername(username);
            long idUser = user.getId();
            session.setAttribute("idUser", idUser);
        }catch (NullPointerException e){

        }
        return "MainPage";
    }


    @RequestMapping(value = "/check-courses", method = RequestMethod.POST)
    @ResponseBody
    public List<Course> checkCourses() {
        Iterable<Course> course = courseRepository.findAll();
        List<Course> courses = new ArrayList<>();
        Iterator<Course> iterator = course.iterator();
        while (iterator.hasNext()) {
            courses.add(iterator.next());
        }
        return courses ;
    }

    @GetMapping("/main/Courses-{idCourse}")
    public String BlockUpdate(@PathVariable("idCourse")long idCourse, Model model, HttpSession session)
    {
        session.setAttribute("idCourse", idCourse);
        Optional<Course> course = courseRepository.findById(idCourse);
        ArrayList<Course> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("Course", res);
        return "MainPageCourseView";
    }
    @RequestMapping(value = "/check-blocks-main", method = RequestMethod.POST)
    @ResponseBody
    public List<Block> checkBlocks(HttpSession session) {
        Long idCourse = (Long) session.getAttribute("idCourse");
        Optional<Course> course = courseRepository.findById(idCourse);
        List<Block> blocks = blockRepository.findBlocksByCourseID(course.get()) ; // blockRepository - экземпляр репозитория, отвечающего за блоки в базе данных

        return blocks ;
    }
    @RequestMapping(value = "/check-lectures-main/{id}", method = RequestMethod.POST)
    @ResponseBody
    public List<Lecture> checkLectures(@PathVariable("id") Long idBlock) {
        Optional<Block> block = blockRepository.findById(idBlock);
        List<Lecture> lectures = lectureRepository.findLecturesByBlockID(block.get()); // blockRepository - экземпляр репозитория, отвечающего за блоки в базе данных

        return lectures ;
    }
    @RequestMapping(value = "/check-coursesType-{id}", method = RequestMethod.POST)
    @ResponseBody
    public String checkCoursesType(@PathVariable("id") Long idCourse) {
        Optional<Course> course = courseRepository.findById(idCourse);
        String courseType = course.get().getCourseTypeID().getNameOfCourseType();
        return courseType;
    }

    @RequestMapping(value = "/check-formOf-{id}", method = RequestMethod.POST)
    @ResponseBody
    public String checkFormOfEducation(@PathVariable("id") Long idCourse) {
        Optional<Course> course = courseRepository.findById(idCourse);
        String formOfEducation = course.get().getFormOfEducationID().getTypeOfEducation();
        return formOfEducation;
    }
    @RequestMapping(value = "/create-users-courses", method = RequestMethod.POST)
    @ResponseBody
    public String CreateUsersCourses(@RequestParam("idCourse") Long idCourse, Model model, HttpSession session) {
        Long idUser = (Long) session.getAttribute("idUser");
        session.setAttribute("idCourse", idCourse);
        User user = userRepository.findUserByid(idUser);
        Course course = courseRepository.findCourseByIdCourse(idCourse);
        List<UsersCourses> usersCoursesByUser = usersCoursesRepository.findUsersCoursesByUserID(user);
        List<UsersCourses> usersCoursesByCourse = usersCoursesRepository.findUsersCoursesByCourseID(course);
        boolean isUserEnrolled = false;
        for (UsersCourses uc : usersCoursesByUser) {
            if (uc.getCourseID().equals(course)) {
                isUserEnrolled = true;
                break;
            }
        }
        boolean isCourseEnrolled = false;
        for (UsersCourses uc : usersCoursesByCourse) {
            if (uc.getUserID().equals(user)) {
                isCourseEnrolled = true;
                break;
            }
        }
        if (!isUserEnrolled && !isCourseEnrolled) {
            UsersCourses usersCourses = new UsersCourses();
            usersCourses.setCourseID(course);
            usersCourses.setUserID(user);
            usersCourses.setCompleteness(false);
            usersCoursesRepository.save(usersCourses);

            return "Успешное добавление курса";
        } else {
            return "Данный курс уже есть";
        }
    }

    @RequestMapping(value = "/check-inProgress-courses", method = RequestMethod.POST)
    @ResponseBody
    public List<Course> checkCoursesInProgress(HttpSession session) {
        Long idUser = (Long) session.getAttribute("idUser");
        List<Course> courses = courseRepository.findAllByUserId(idUser);
        List<Course> inProgressCourses = new ArrayList<>();
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        for (Course course : courses) {
            UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
            if (!usersCourses.isCompleteness()) {
                inProgressCourses.add(course);
            }
        }
        return inProgressCourses;
    }

    @RequestMapping(value = "/check-completes-courses", method = RequestMethod.POST)
    @ResponseBody
    public List<Course> checkCompleteCourses(HttpSession session) {
        Long idUser = (Long) session.getAttribute("idUser");
        List<Course> courses = courseRepository.findAllByUserId(idUser);
        List<Course> completedCourses = new ArrayList<>();
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        for (Course course : courses) {
            UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
            if (usersCourses.isCompleteness()) {
                completedCourses.add(course);
            }
        }
        return completedCourses;
    }
    @RequestMapping(value = "/check-test-success-main", method = RequestMethod.POST)
    @ResponseBody
    public String[] handleTestSuccess(@RequestBody Long idCourse,HttpSession session) {
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        Course course = courseRepository.findCourseByIdCourse(idCourse);
        UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user, course);
        if (usersCourses.getTestResults()!=null){
            String testResults = usersCourses.getTestResults();
            List<String> resultList = new ArrayList<>(Arrays.asList(testResults.split("\\s*-|\\s*\\(|\\s*\\)")));
            resultList.add(idCourse.toString());
            String[] resultArray = resultList.toArray(new String[0]);
            return resultArray;
        }
        return null;
    }

    @GetMapping("/create-certificate")
    public ResponseEntity<byte[]>  createCertificate(@RequestParam Long idCourse, HttpSession session) throws IOException, DocumentException {
        User user = userRepository.findUserByid((Long) session.getAttribute("idUser"));
        Course course = courseRepository.findCourseByIdCourse(idCourse);
        UsersCourses usersCourses = usersCoursesRepository.findUsersCoursesByUserIDAndCourseID(user,course);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        String format = "yyyyMMddHHmmss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String fileName = dateFormat.format(new Date()) + "_" + new Random().nextInt(900000) + 100000 + ".pdf";

        String currentDir = "./src/main/resources/static/images/certificates/";
        String filePath = currentDir + fileName;

        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(filePath);

        PdfWriter writer = PdfWriter.getInstance(document, fos);
        document.setPageSize(PageSize.A4.rotate());
        document.open();

        Image backgroundImage = Image.getInstance("./src/main/resources/static/images/шаблон.jpg");

        backgroundImage.setAbsolutePosition(0, 0);
        backgroundImage.scaleAbsolute(document.getPageSize());

        PdfTemplate backgroundTemplate = writer.getDirectContent().createTemplate(document.getPageSize().getWidth(), document.getPageSize().getHeight());
        backgroundTemplate.addImage(backgroundImage);

        PdfPageEventHelper eventHelper = new PdfPageEventHelper() {
            public void onEndPage(PdfWriter writer, Document document) {
                try {
                    writer.getDirectContentUnder().addTemplate(backgroundTemplate, 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        writer.setPageEvent(eventHelper);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        BaseFont neutralFace = BaseFont.createFont("./src/main/resources/static/fonts/NeutralFace.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font courseFont = new Font(neutralFace, 10, Font.NORMAL);
        Paragraph textParagraph = new Paragraph("Настоящим удостоверяется, что", courseFont);
        textParagraph.setAlignment(Element.ALIGN_CENTER);
        textParagraph.setSpacingBefore(75);
        document.add(textParagraph);

        Font nameFont = new Font(neutralFace, 30, Font.BOLD);
        Paragraph nameParagraph = new Paragraph(user.getUserSur().toUpperCase()+" "+user.getUserNamee().toUpperCase()+" "+user.getUserPatr().toUpperCase(), nameFont);
        nameParagraph.setAlignment(Element.ALIGN_CENTER);
        nameParagraph.setSpacingBefore(25);
        document.add(nameParagraph);

        Paragraph courseParagraph = new Paragraph("завершил(а) образовательный курс", courseFont);
        courseParagraph.setAlignment(Element.ALIGN_CENTER);
        courseParagraph.setSpacingBefore(50);
        document.add(courseParagraph);

        Font courseNameFont = new Font(neutralFace, 20, Font.BOLD);
        Paragraph courseNameParagraph = new Paragraph(course.getNameOfCourse().toUpperCase(), courseNameFont);
        courseNameParagraph.setAlignment(Element.ALIGN_CENTER);
        courseNameParagraph.setSpacingAfter(75);
        document.add(courseNameParagraph);

        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));
        String formattedDate = currentDate.format(dateFormatter);

        Paragraph dateParagraph = new Paragraph(formattedDate, courseFont);
        dateParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(dateParagraph);

        String fileNameWithout = fileName.substring(0, fileName.length() - 4);

        Paragraph certificateNumberParagraph = new Paragraph(fileNameWithout, courseFont);
        certificateNumberParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(certificateNumberParagraph);

        usersCourses.setCertificateNumber(fileNameWithout);
        usersCoursesRepository.save(usersCourses);

        document.close();
        writer.close();

        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        return response;
    }
}
