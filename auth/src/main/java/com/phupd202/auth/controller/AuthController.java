package com.phupd202.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.phupd202.auth.dto.SignUpDto;
import com.phupd202.auth.service.SignUpService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/library-online/auth")
public class AuthController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping("/signup")
    public String signup(Model model) {
        try {
            SignUpDto signUpDto = new SignUpDto();
            model.addAttribute("signUpDto", signUpDto);
            return "sign-up";
        } catch(Exception e) {
            model.addAttribute("error", "Encounterd a problem while sign up!!");
            return "error";
        }
    }

    @PostMapping("/signup")
    public String sigin(@ModelAttribute SignUpDto signUpDto) {
        if(!signUpService.isExistedAccount(signUpDto)) {
            signUpService.saveAccount(signUpDto);
            return "redirect:/library-online/home";
        }
        return "error";
    } 
}
