package com.phupd202.auth.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phupd202.auth.dto.AuthoritiesDto;
import com.phupd202.auth.dto.UpdateAccountDto;
import com.phupd202.auth.service.AdminGetAccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/library-online/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Constructor Injection
    private final AdminGetAccountService adminGetAccountService;

    public AdminController(AdminGetAccountService adminGetAccountService) {
        this.adminGetAccountService = adminGetAccountService;
    }

    // using model.addAttribute()
    @GetMapping("/list-user")
    public String listUsers(Model model) {
        List<AuthoritiesDto> authorities = adminGetAccountService.findAllAccountService();
        model.addAttribute("authorities", authorities);
        return "admin-list-user";
    }

    @PostMapping("/list-user")
    public String updateAccount(@RequestBody UpdateAccountDto requestData) {
        Long accountId = requestData.getAccountId();
        String action = requestData.getAction();

        logger.info("Dữ liệu action" + action);
        logger.info("Dữ liệu accountId" + accountId);

        if ("delete".equals(action)) {
            adminGetAccountService.deleteAccount(accountId);
        } else if ("recovery".equals(action)) {
            adminGetAccountService.recoveryAccount(accountId);
        }

        return "admin-list-user";
    }

    // @GetMapping("/list-user")
    // public ResponseEntity<List<AuthoritiesDto>> listUser() {
    // List<AuthoritiesDto> authorities =
    // adminGetAccountService.findAllAccountService();
    // return new ResponseEntity<>(authorities, HttpStatus.OK);
    // }

    // @PostMapping("/list-user")
    // @ResponseBody
    // public String deletedAccount(@RequestBody UpdateAccountDto request) {
    // String action = request.getAction();
    // Long accountId = request.getAccountId();

    // if(action.equals("delete")) {
    // adminGetAccountService.deleteAccount(accountId);
    // }

    // if(action.equals("recovery")) {
    // adminGetAccountService.recoveryAccount(accountId);
    // }
    // return "Success";
    // }

}
