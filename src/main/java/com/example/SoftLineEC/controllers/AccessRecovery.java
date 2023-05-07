package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.models.ValidPassword;
import com.example.SoftLineEC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
public class AccessRecovery {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/accessRecovery")
    public String AccessRecovery() {
        return "AccessRecovery";
    }

    @RequestMapping(value = "/user-data-recovery", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> UserDataRecovery(@RequestBody String email, HttpSession session) {
        String sanitizedEmail = email.replace("\"", "");
        User user = userRepository.findUserByUsername(sanitizedEmail);
        if (user != null) {
            session.setAttribute("idUser", user.getId());
            return ResponseEntity.ok("Пользователь есть");
        } else {
            return ResponseEntity.ok("Пользователя нет");
        }
    }

    @RequestMapping(value = "/user-data-recovery-code-send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> UserDataRecoveryCode(@RequestBody Map<String, List<String>> data, HttpSession session) {
        List<String> username = data.get("username");
        List<String> phone = data.get("phone");
        User user = userRepository.findUserByUsername(username.get(0));
        if (user.getPhoneNumber().equals(phone.get(0))) {
            String mailUsername = "isip_a.a.aksenov@mpt.ru";
            String mailPassword = "lh.gf2003";
            Properties props = new Properties();
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailUsername, mailPassword);
                        }
                    });
            int randomNumber = (int) (Math.random() * 900000) + 100000;
            String messageText = "Ваш код подтверждения: " + randomNumber;
            session.setAttribute("randomNumber", randomNumber);
            try {
                Message message = new MimeMessage(mailSession);
                message.setFrom(new InternetAddress(mailUsername));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(username.get(0)));
                message.setSubject("Код подтверждения");
                message.setText(messageText);
                Transport.send(message);
                return ResponseEntity.ok("Пароль отправлен");
            } catch (MessagingException e) {
                return ResponseEntity.ok(e.toString());
            }
        } else {
            return ResponseEntity.ok("Неверный номер телефона");
        }
    }

    @RequestMapping(value = "/user-data-confirmation-message", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> ConfirmationMessage(@RequestBody String confirmationCode, HttpSession session) {
        int randomNumber = (int) session.getAttribute("randomNumber");
        String sanitizedConfirmationCode = confirmationCode.replace("\"", "").replaceAll("\\s+","");
        int intConfirmationCode  = Integer.parseInt(sanitizedConfirmationCode);
        if (intConfirmationCode == randomNumber){
            return ResponseEntity.ok("Верный код подтверждения");
        }else {
            return ResponseEntity.ok("Неверный код подтверждения");
        }
    }

    @RequestMapping(value = "/user-data-password-send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> NewPassword(@RequestBody String newPassword, HttpSession session) {
        Long idUser = (Long) session.getAttribute("idUser");
        User user = userRepository.findUserByid(idUser);
        String sanitizedPassword = newPassword.replace("\"", "");
        try{
            user.setPassword(passwordEncoder.encode(sanitizedPassword));
            user.setRepeatPassword(sanitizedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("Пароль изменен успешно");
        }catch (Exception e){
            return ResponseEntity.ok("Пароль не изменен");
        }
    }
}
