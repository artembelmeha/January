package ua.testing.demo_jpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.testing.demo_jpa.dto.UserDTO;
import ua.testing.demo_jpa.dto.UsersDTO;
import ua.testing.demo_jpa.entity.RoleType;
import ua.testing.demo_jpa.entity.User;
import ua.testing.demo_jpa.service.UserService;

import java.util.Optional;

@Slf4j
@Controller
public class LoginFormController {

    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginFormController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String registerFormController(@ModelAttribute User user,Model model) {
        user.setRole(RoleType.ROLE_USER);
        log.info("{}", user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        userService.saveNewUser(user);
        model.addAttribute("user", user);
        return "users-list";
    }
    @PostMapping("/login")
    public String loginFormController(@ModelAttribute UserDTO user) {
        log.info("{}", user);
        Optional<User> optionalUser = userService.findByUserLogin(user);;
        if(optionalUser.isPresent()) {
            return "redirect:/index";
        }
        return "home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("people", userService.getAllUsers());
        return "/users-list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        log.info("{}", userService.getAllUsers());
        model.addAttribute("user" , userService.readById(id));
        return "/users";
    }

}