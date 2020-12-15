package com.example.springbootsimplefactorauth.controller;

import com.example.springbootsimplefactorauth.model.AppUser;
import com.example.springbootsimplefactorauth.model.Token;
import com.example.springbootsimplefactorauth.repo.AppUserRepo;
import com.example.springbootsimplefactorauth.repo.TokenRepo;
import com.example.springbootsimplefactorauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;


@Controller
public class UserController {

    private UserService userService;
    private AppUserRepo appUserRepo;
    private TokenRepo tokenRepo;

    @Autowired
    public UserController(UserService userService, TokenRepo tokenRepo, AppUserRepo appUserRepo) {
        this.userService = userService;
        this.tokenRepo = tokenRepo;
        this.appUserRepo = appUserRepo;
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
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        Object details = SecurityContextHolder
                .getContext().getAuthentication().getDetails();
        model.addAttribute("authorities", authorities);
        model.addAttribute("details", details);
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

    @GetMapping("/token")
    public String token(@RequestParam String value) {
        Token byValue = tokenRepo.findByValue(value);
        AppUser appUser = byValue.getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
        return "hello";
    }
}
