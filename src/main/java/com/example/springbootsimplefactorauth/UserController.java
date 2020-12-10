package com.example.springbootsimplefactorauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // zeby dzialalo zwracanie typu - tutaj stringa to musi byc albo @RestController nad klasa albo nad metoda @ResponseBody
    // rest controller zwraca np jsony tez - generalnie zwraca typ metody - tak sie zdaje
//    @GetMapping("/hello")
//    @ResponseBody
//    public String hello() {
//        return "hello";
//    }


    // zeby dzialalo zwracanie pliku - tutaj odwolanie do html nie moze byc @RestController nad klasa tylko samo @Controller
    @GetMapping("/hello")
    public String hello(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "hello";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("user", new AppUser());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(AppUser appUser) {
        userService.addUser(appUser);
        return "sign-up";
    }
}
