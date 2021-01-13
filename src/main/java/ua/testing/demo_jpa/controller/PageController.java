package ua.testing.demo_jpa.controller;

//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import ua.testing.demo_jpa.dto.UserDTO;
import ua.testing.demo_jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.testing.demo_jpa.service.UserService;


@Controller
public class PageController {

    UserService userService;

    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/","home","/logout"})
    public String homePage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "home";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/api")
    public String mainPage(){
        return "index.html";
    }

    @GetMapping("/all_user")
    public String userPage(){
        return "users/index.html";
    }



}
