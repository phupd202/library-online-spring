package com.phupd202.auth.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phupd202.auth.dto.AuthoritiesDto;
import com.phupd202.auth.service.AdminAuthoritiesService;

@RequestMapping("/library-online/admin")
@Controller
public class AdminController {
    // Constructor Injection
    private final AdminAuthoritiesService adminGetAccountService;

    public AdminController(AdminAuthoritiesService adminGetAccountService) {
        this.adminGetAccountService = adminGetAccountService;
    }

    @GetMapping("/user")
    public String listUsers(Model model) {
        List<AuthoritiesDto> authorities = adminGetAccountService.findAllAccountService();
        model.addAttribute("authorities", authorities);
        return "admin-list-user";
    }
}
