package com.phupd202.auth.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phupd202.auth.common.Constant;
import com.phupd202.auth.dto.SignUpDto;
import com.phupd202.auth.entity.Account;
import com.phupd202.auth.entity.Role;
import com.phupd202.auth.exception.InvalidAccountException;
import com.phupd202.auth.entity.Authorities;
import com.phupd202.auth.repository.AccountRepository;
import com.phupd202.auth.repository.AuthoritiesRepository;
import com.phupd202.auth.repository.RoleRepository;
import com.phupd202.auth.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(SignUpServiceImpl.class);

    @Override
    public Boolean isExistedAccount(SignUpDto signUpDto) {
        String emailToCheck = signUpDto.getEmail();

        // Find account in the database
        try {
            return accountRepository.findByEmail(emailToCheck) != null;
        } catch (DataAccessException e) {
            logger.error("Error checking for existing account", e);
            return false;
        }
    }

    @Override
    public Boolean isValidPassword(SignUpDto signUpDto) {
        return signUpDto.getPassword().equals(signUpDto.getConfirmPassword());
    }

    @Override
    public Boolean saveAccount(SignUpDto signupDto) throws InvalidAccountException {
        // Account is existed or invalid password
        if(isExistedAccount(signupDto) || !isValidPassword(signupDto)) {
            throw new InvalidAccountException("Tài khoản đã tồn tại hoặc mật khẩu không hợp lệ!!");
        }

        Account savedAccount = new Account();

        savedAccount.setEmail(signupDto.getEmail());
        savedAccount.setPhone(signupDto.getPhone());
        savedAccount.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        savedAccount.setIsActive(false);
        savedAccount.setIsDeleted(false);

        // SAVE DATA
        try {
            Role defaultRole = roleRepository.findRoleByNameRole(Constant.ROLE_DEFAUL);
            
            Authorities defaulRole = new Authorities();
            defaulRole.setRole(defaultRole);
            defaulRole.setAccount(savedAccount);

            // prepare data to save into database
            List<Authorities> roleOfUser = new ArrayList<Authorities>();
            roleOfUser.add(defaulRole);
            
            savedAccount.setRoleAccounts(roleOfUser);

            //save data into database
            accountRepository.save(savedAccount);
            authoritiesRepository.save(defaulRole);
            return true;
        } catch (DataIntegrityViolationException ex) {
            // Print logging
            logger.error("Data integrity violation: {}", ex.getMessage());
            return false;
        }
    }

}
