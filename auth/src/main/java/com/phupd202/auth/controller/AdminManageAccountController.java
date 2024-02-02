package com.phupd202.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phupd202.auth.dto.ModifierDto;
import com.phupd202.auth.dto.UpdateAccountDto;
import com.phupd202.auth.service.AdminAuthoritiesService;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/library-online/admin/user")
public class AdminManageAccountController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Constructor Injection
    private final AdminAuthoritiesService adminAuthoritiesService;

    public AdminManageAccountController(AdminAuthoritiesService adminAuthoritiesService) {
        this.adminAuthoritiesService = adminAuthoritiesService;
    }

    // Xoá và khôi phục tài khoản
    @RequestMapping(value = { "/delete-user", "/recovery-user" }, method = RequestMethod.POST)
    public ResponseEntity<String> deleteAccount(@RequestBody UpdateAccountDto requestData) {
        Long accountId = requestData.getAccountId();
        String action = requestData.getAction();

        // Logging
        logger.info("Dữ liệu action: " + action);
        logger.info("Kiểu dữ liệu của accoundId có phải Long không??: " + (accountId instanceof Long));
        logger.info("Dữ liệu accountId: " + accountId);

        try {
            if ("delete".equals(action)) {
                adminAuthoritiesService.deleteAccount(accountId);
                return new ResponseEntity<>("Delete account successfully!", HttpStatus.OK);
            }

            if (action.equals("recovery")) {
                adminAuthoritiesService.recoveryAccount(accountId);
                return new ResponseEntity<>("Recovery account successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid action provided", HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            logger.error("Có lỗi khi thao tác với cơ sở dữ liệu", e);
            return new ResponseEntity<>("Update infomation failure!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cập nhật thông tin tài khoản
    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public ResponseEntity<String> editAccount(@RequestBody ModifierDto requestData) {
        String email = requestData.getEmail();
        String phone = requestData.getPhone();
        String role = requestData.getRole();

        logger.info("Email of edited account: ", email);
        logger.info("Phone of edited account: ", phone);
        logger.info("Role of edited account: ", role);

        try {
            adminAuthoritiesService.editAccount(email, phone, role);
            return new ResponseEntity<>("Update information successfully", HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("Có lỗi khi cập nhật lại tài khoản, check Service and Repo", e);
            return new ResponseEntity<>("Update information failure", HttpStatus.BAD_REQUEST);
        }
    }
}
