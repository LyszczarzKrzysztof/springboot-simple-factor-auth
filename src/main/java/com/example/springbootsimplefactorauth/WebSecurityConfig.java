package com.example.springbootsimplefactorauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // te dwa ponizej - wylaczenie crosssite request foreign (przypadkowe wyslanie formularza)
        // i blokowanie naglowkow - wylacza sie po to zeby dzialalo zapisywanie uzytkownikow do h2 db
        // hasRole - podajemy role bez przedrostka ROLE_
        // hasAuthoriyt - podajemy role z przedrostkiemr ROLE_
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .antMatchers("/hello").authenticated()
                .antMatchers("/for-user").hasAuthority("ROLE_USER")
                .antMatchers("/for-admin").hasRole("ADMIN")
                .and()
                .formLogin()
                .defaultSuccessUrl("/hello");
    }
}
