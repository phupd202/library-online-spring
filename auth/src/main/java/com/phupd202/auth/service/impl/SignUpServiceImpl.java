package com.phupd202.auth.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phupd202.auth.dto.SignUpDto;
import com.phupd202.auth.entity.Account;
import com.phupd202.auth.repository.AccountRepository;
import com.phupd202.auth.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean isExistedAccount(SignUpDto signUpDto) {
        String username = signUpDto.getEmail();
        String email = signUpDto.getUsername();

        // Finding in database
        List<Account> existedUsername = accountRepository.findByUsername(username);
        List<Account> existedEmail = accountRepository.findByEmail(email);

        if (existedEmail.isEmpty() && existedUsername.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean saveAccount(SignUpDto signupDto) {
        Account savedAccount = new Account();

        savedAccount.setUsername(signupDto.getUsername());
        savedAccount.setEmail(signupDto.getEmail());
        savedAccount.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        savedAccount.setCreatedAt(now);

        savedAccount.setIsActive(false);

        try {
            accountRepository.save(savedAccount);
            return true;
        } catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
