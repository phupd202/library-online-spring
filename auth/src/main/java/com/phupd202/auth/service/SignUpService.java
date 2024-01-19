package com.phupd202.auth.service;

import com.phupd202.auth.dto.SignUpDto;
import com.phupd202.auth.exception.InvalidAccountException;

public interface SignUpService {
    Boolean isExistedAccount(SignUpDto signUpDto);

    Boolean saveAccount(SignUpDto signupDto) throws InvalidAccountException;

    Boolean isValidPassword(SignUpDto signUpDto);
}
