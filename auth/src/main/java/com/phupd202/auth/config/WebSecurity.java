package com.phupd202.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class WebSecurity {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/resources/**", 
                                        "/static/**", 
                                        "/css/**", 
                                        "/js/**").permitAll()
                        .antMatchers("/library-online/signup").permitAll()
                        .antMatchers("/library-online/signin").permitAll()
                        .antMatchers("/library-online/home").permitAll()
                        .antMatchers("/library-online/admin/**").hasRole("ADMIN")
                        .antMatchers("/library-online/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                );
                        
        return http.build();
    }
}
