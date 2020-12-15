package com.example.springbootsimplefactorauth;

import com.example.springbootsimplefactorauth.model.AppUser;
import com.example.springbootsimplefactorauth.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Start {

    @Autowired
    public Start(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        AppUser appUserJanusz = new AppUser();
        appUserJanusz.setUsername("Janusz");
        appUserJanusz.setPassword(passwordEncoder.encode("Janusz123"));
        appUserJanusz.setRole("ROLE_ADMIN");
        appUserJanusz.setEnabled(true);
        appUserRepo.save(appUserJanusz);

        AppUser appUserBogdan = new AppUser();
        appUserBogdan.setUsername("Bogdan");
        appUserBogdan.setPassword(passwordEncoder.encode("Bogdan123"));
        appUserBogdan.setRole("ROLE_USER");
        appUserBogdan.setEnabled(true);
        appUserRepo.save(appUserBogdan);
    }
}
