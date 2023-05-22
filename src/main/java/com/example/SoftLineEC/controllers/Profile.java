package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.*;
import com.example.SoftLineEC.repositories.AddressRepository;
import com.example.SoftLineEC.repositories.UserRepository;
import com.example.SoftLineEC.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Контроллер для работы с профилем пользователя.
 */
@Controller
public class Profile {
    /**
     * Репозиторий пользователей.
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * Репозиторий адресов.
     */
    @Autowired
    private AddressRepository addressRepository;
    /**
     * Интерфейс для кодирования паролей пользователей.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Отображает главную страницу.
     * @return имя представления для главной страницы.
     */
    @GetMapping("/ProfileInfo")
    public String mainPage() {
        return "ProfileMainPage";
    }
    /**
     * Обрабатывает GET-запрос на получение информации о пользователе.
     * @param authentication объект Authentication, используемый для получения имени текущего пользователя
     * @param session объект HttpSession, используемый для хранения идентификатора текущего пользователя
     * @return объект User, соответствующий текущему пользователю
     */
    @RequestMapping(value = "/check-user-info", method = RequestMethod.GET)
    @ResponseBody
    public User checkUser(Authentication authentication, HttpSession session) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findUserByUsername(username);
        long idUser = user.getId();
        session.setAttribute("idUser", idUser);
        return user;
    }
    /**
     * Обрабатывает POST-запрос на изменение фотографии пользователя.
     * @param file объект MultipartFile, содержащий загружаемый файл
     * @param session объект HttpSession, используемый для получения идентификатора текущего пользователя
     * @return объект ResponseEntity, содержащий текстовое сообщение об успешной загрузке файла
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @RequestMapping(value = "/handleUserPhotoEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        Long idUser = (Long) session.getAttribute("idUser");
        Optional<User> OldUser = userRepository.findById(idUser);
        User user = userRepository.findUserByid(idUser);
        user.setUserSur(OldUser.get().getUserSur());
        user.setUserNamee(OldUser.get().getUserNamee());
        user.setUserPatr(OldUser.get().getUserPatr());
        user.setUsername(OldUser.get().getUsername());
        user.setPassword(OldUser.get().getPassword());
        user.setRepeatPassword(OldUser.get().getRepeatPassword());
        user.setActive(OldUser.get().isActive());
        user.setRoles(OldUser.get().getRoles());
        user.setPhoneNumber(OldUser.get().getPhoneNumber());
        user.setEdInstitution(OldUser.get().getEdInstitution());
        FileUploadService.saveFile(file);
        user.setPhotoLink(FileUploadService.getFilePath2());
        userRepository.save(user);

        return ResponseEntity.ok("File(s) uploaded successfully");
    }
    /**
     * Обрабатывает GET-запрос на получение страницы редактирования профиля пользователя.
     * @param session объект HttpSession, используемый для получения идентификатора текущего пользователя
     * @param model объект Model, используемый для передачи данных на страницу
     * @return имя представления для страницы редактирования профиля
     */
    @GetMapping("/ProfileInfo/Update")
    public String updView(HttpSession session, Model model) {
        Long idUser = (Long) session.getAttribute("idUser");
        model.addAttribute("user_object", userRepository.findById(idUser).orElseThrow());
        Address address = addressRepository.findAddressesByUserID(userRepository.findUserByid(idUser)).orElseGet(Address::new);
        model.addAttribute("address", address);
        return "ProfileEdit";
    }
    /**
     * Обрабатывает POST-запрос на сохранение изменений профиля пользователя.
     * @param data объект Map, содержащий данные о пользователе и его адресе
     * @param session объект HttpSession, используемый для получения идентификатора текущего пользователя
     * @return строковое значение "OK", если сохранение прошло успешно
     */
    @RequestMapping(value = "/handleProfileInfoEdit", method = RequestMethod.POST)
    @ResponseBody
    public String handleProfileInfoEdit(@RequestBody Map<String, Object> data, HttpSession session) {
        List<String> surname = (List<String>) data.get("surname");
        List<String> name = (List<String>) data.get("name");
        List<String> patr = (List<String>) data.get("patr");
        List<String> dateOfBirth = (List<String>) data.get("dateOfBirth");
        List<String> country = (List<String>) data.get("country");
        List<String> index = (List<String>) data.get("index");
        List<String> city = (List<String>) data.get("city");
        List<String> region = (List<String>) data.get("region");
        List<String> street = (List<String>) data.get("street");
        List<String> house = (List<String>) data.get("house");
        List<String> frame = (List<String>) data.get("frame");
        List<String> apartment = (List<String>) data.get("apartment");
        List<String> phoneNumber = (List<String>) data.get("phoneNumber");
        List<String> edInstitution = (List<String>) data.get("edInstitution");
        for (int i = 0; i < surname.size(); i++) {
            Long idUser = (Long) session.getAttribute("idUser");
            Optional<User> OldUser = userRepository.findById(idUser);
            User user = userRepository.findUserByid(idUser);
            user.setUserSur(surname.get(i));
            user.setUserNamee(name.get(i));
            user.setUserPatr(patr.get(i));
            String dateString = dateOfBirth.get(i);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate;
            try {
                utilDate = format.parse(dateString);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                user.setDateOfBirth(sqlDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            user.setUsername(OldUser.get().getUsername());
            user.setPassword(OldUser.get().getPassword());
            user.setRepeatPassword(OldUser.get().getRepeatPassword());
            user.setActive(OldUser.get().isActive());
            user.setRoles(OldUser.get().getRoles());
            user.setPhoneNumber(phoneNumber.get(i));
            user.setEdInstitution(edInstitution.get(i));
            user.setPhotoLink(OldUser.get().getPhotoLink());
            userRepository.save(user);
            try {
                Address address = addressRepository.findAddressByUserID(userRepository.findUserByid(idUser));
                address.setIndexOfAddress(index.get(i));
                address.setCountyOfAddress(country.get(i));
                address.setCityOfAddress(city.get(i));
                address.setRegionOfAddress(region.get(i));
                address.setStreetOfAddress(street.get(i));
                address.setHouseOfAddress(house.get(i));
                address.setFrameOfAddress(frame.get(i));
                address.setApartmentOfAddress(apartment.get(i));
                addressRepository.save(address);
            } catch (NullPointerException ex) {
                Address address = new Address();
                address.setIndexOfAddress(index.get(i));
                address.setCountyOfAddress(country.get(i));
                address.setCityOfAddress(city.get(i));
                address.setRegionOfAddress(region.get(i));
                address.setStreetOfAddress(street.get(i));
                address.setHouseOfAddress(house.get(i));
                address.setFrameOfAddress(frame.get(i));
                address.setApartmentOfAddress(apartment.get(i));
                address.setUserID(userRepository.findUserByid(idUser));
                addressRepository.save(address);
            }
        }
        return "OK";
    }
    /**
     * Обрабатывает POST-запрос на изменение авторизационных данных пользователя (логина и пароля).
     * @param data объект Map, содержащий новый логин и пароль пользователя
     * @param bindingResult объект BindingResult, содержащий результаты валидации данных
     * @param session объект HttpSession, используемый для получения идентификатора текущего пользователя
     * @return объект ResponseEntity с текстовым сообщением об успешном изменении данных или об ошибках валидации
     */
    @RequestMapping(value = "/handleProfileAuthInfoEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> handleProfileAuthInfoEdit(@RequestBody @Valid Map<String, List<@ValidPassword String>> data, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.ok("0000");
        }
        List<String> username = data.get("username");
        List<String> oldPassword = data.get("oldPassword");
        List<String> newPassword = data.get("newPassword");
        Long idUser = (Long) session.getAttribute("idUser");
        User user_from_db = userRepository.findUserByid(idUser);
        if (!user_from_db.getUsername().equals(username.get(0))) {
            User userFromDb = userRepository.findUserByUsername(username.get(0));
            if (userFromDb != null) {
                return ResponseEntity.ok("Пользователь с таким логином уже существует");
            }
        }
        if (!user_from_db.getRepeatPassword().equals(oldPassword.get(0))) {
            return ResponseEntity.ok("Пароль не совпадает с настоящим");
        }
        user_from_db.setUsername(username.get(0));
        user_from_db.setPassword(passwordEncoder.encode(newPassword.get(0)));
        user_from_db.setRepeatPassword(newPassword.get(0));
        userRepository.save(user_from_db);
        return ResponseEntity.ok("Данные успешно сохранены");
    }
    /**
     * Обрабатывает DELETE-запрос на удаление профиля пользователя.
     * @param session объект HttpSession, используемый для получения идентификатора текущего пользователя
     * @return объект ResponseEntity с текстовым сообщением об успешном или неуспешном удалении профиля
     */
    @DeleteMapping("/handleUserDelete")
    public ResponseEntity<String> deleteUser(HttpSession session) {
        try {
            Long idUser = (Long) session.getAttribute("idUser");
            User user = userRepository.findUserByid(idUser);
            user.setActive(false);
            userRepository.save(user);
            return new ResponseEntity<>("Пользователь успешно удален", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Пользователь не удален", HttpStatus.OK);
        }
    }

}
