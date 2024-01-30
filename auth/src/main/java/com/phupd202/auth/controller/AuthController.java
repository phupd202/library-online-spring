package com.phupd202.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phupd202.auth.dto.SignUpDto;
import com.phupd202.auth.service.SignUpService;

@Controller
@RequestMapping("/library-online")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final SignUpService signUpService;
    
    public AuthController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        try {
            SignUpDto signUpDto = new SignUpDto();
            model.addAttribute("signUpDto", signUpDto);
            return "sign-up";
        } catch (Exception e) {
            logger.error("Error during signup", e);
            model.addAttribute("error", "Encountered a problem while signing up!!");
            return "error";
        }
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signUpDto") SignUpDto signUpDto) {
        try {
            if (!signUpService.isExistedAccount(signUpDto)) {
                signUpService.saveAccount(signUpDto);
                logger.info("User signed up successfully: {}", signUpDto.getEmail());
                return "redirect:/library-online/home";
            }
            return "error";
        } catch (Exception e) {
            logger.error("Error during signup", e);
            return "error";
        }
    }

    @GetMapping("/signin")
    public String getMethodName(Model model) {
        return "sign-in";
    }
}
