package com.example.springbootsimplefactorauth.service;

import com.example.springbootsimplefactorauth.model.AppUser;
import com.example.springbootsimplefactorauth.model.Token;
import com.example.springbootsimplefactorauth.repo.AppUserRepo;
import com.example.springbootsimplefactorauth.repo.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService {

    private TokenRepo tokenRepo;

    private MailService mailService;
    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
        sendToken(appUser);
    }

    private void sendToken(AppUser appUser) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        tokenRepo.save(token);
        String url = "http://localhost:8080/token?value=" + tokenValue;
        try {
            mailService.sendMail(appUser.getUsername(), "Potwierdzaj to!", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
