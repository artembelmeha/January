package ua.testing.demo_jpa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public LoginFormController(UserService userService) {
        this.userService = userService;
    }

    //    @ResponseStatus(HttpStatus.CREATED)
    //@RequestMapping(value = "login", method = RequestMethod.POST)
    @PostMapping(value = "/register")
    public String registerFormController(@ModelAttribute User user,Model model) {
        user.setRole(RoleType.ROLE_USER);
        log.info("{}", user);
        userService.saveNewUser(user);
        model.addAttribute("user", user);
        return "users-list";
    }
    @PostMapping(value = "/login")
    public String loginFormController(@ModelAttribute UserDTO user,Model model) {
        log.info("{}", user);
        Optional<User> optionalUser = userService.findByUserLogin(user);;
        if(optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "users-list";
        }
        return "home";
    }
    @GetMapping(value = "/user")
    public UsersDTO getAllUser() {
        log.info("{}", userService.getAllUsers());
        return userService.getAllUsers();
    }



}