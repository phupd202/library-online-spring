package com.phupd202.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class WebSecurity {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurity.class);

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authz) -> authz
                        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**").permitAll()
                        .antMatchers("/library-online/signup").permitAll()
                        .antMatchers("/library-online/home").permitAll()
                        .antMatchers("/library-online/admin/**").hasRole("ADMIN")
                        .antMatchers("/library-online/user/**").hasRole("USER")
                        .antMatchers("/library-online/select-role").access("hasRole('USER') and hasRole('ADMIN')")
                        .anyRequest().authenticated())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/library-online/signin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/library-online/signin")
                        .successHandler((request, response, authentication) -> {
                            Boolean isUser = false;
                            Boolean isAdmin = false;

                            for (GrantedAuthority auth : authentication.getAuthorities()) {
                                logger.info("User has authority: " + auth.getAuthority());
                                if (auth.getAuthority().equals("ROLE_USER")) {
                                    isUser = true;
                                } else if (auth.getAuthority().equals("ROLE_ADMIN")) {
                                    isAdmin = true;
                                }
                            }

                            if (isUser && isAdmin) {
                                response.sendRedirect("/library-online/select-role");
                            } else if (isUser) {
                                response.sendRedirect("/library-online/user/home");
                            } else if (isAdmin) {
                                response.sendRedirect("/library-online/admin/home");
                            } else {
                                // Chuyển hướng mặc định nếu không có quyền nào được xác định
                                response.sendRedirect("/library-online/default-home");
                            }
                        })
                        .permitAll())
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/library-online/logout"))
                        .permitAll());

        return http.build();
    }
}
