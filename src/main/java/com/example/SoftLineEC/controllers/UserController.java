package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.Role;
import com.example.SoftLineEC.models.User;
import com.example.SoftLineEC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UserController {
    @Autowired
    private com.example.SoftLineEC.repositories.UserRepository userRepository;


    @GetMapping("/a")
    public String userView(Model model)
    {
        model.addAttribute("user_list", userRepository.findAll());
        return "UserMain";
    }

    @GetMapping("/{id}")
    public String detailView(@PathVariable Long id, Model model)
    {
        model.addAttribute("user_object",userRepository.findById(id).orElseThrow());
        return "UserDetail";
    }

//    @GetMapping("/addUser")
//    public String userAdd (Model model)
//    {
//        model.addAttribute("roles", role.values());
//        return "userAdd";
//    }
//
//    @PostMapping("/addUser")
//    public String add_user(@RequestParam String username,
//                           @RequestParam String userSur,
//                           @RequestParam String userNamee,
//                           @RequestParam String userPatr,
//                           @RequestParam(name="roles[]", required = false) String[] roles,
//                           Model model)
//    {
//        user.getRoles().clear();
//        if(roles != null)
//        {
//            for(String Role: roles)
//            {
//                user.getRoles().add(role.valueOf(Role));
//            }
//        }
//
//        //user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        userRepository.save(user);
//        return "redirect:/admin/a";
//    }

    @GetMapping("/{id}/update")
    public String updView(@PathVariable(value = "id") Long id, Model model)
    {
        model.addAttribute("user_object",userRepository.findById(id).orElseThrow());
        model.addAttribute("roles", Role.values());
        return "UserEdit";
    }

    @PostMapping("/{id}/update")
    public String userPostUpdate(@ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 Model modelRoles,
                                 @RequestParam( name="roles[]", required = false) String[] roles,
                                 @PathVariable( value = "id") long id)
    {
        if(bindingResult.hasErrors()) {
            modelRoles.addAttribute("user_object",userRepository.findById(id).orElseThrow());
            modelRoles.addAttribute("roles", Role.values());
            return "UserEdit";
        }
        String password = user.getPassword();
        user.getRoles().clear();
        if(roles == null)
            return "UserEdit";
        if(roles != null)
        {
            for(String role: roles)
            {
                user.getRoles().add(Role.valueOf(role));
            }
        }
        user.setActive(true);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/admin/{id}";
    }

}
