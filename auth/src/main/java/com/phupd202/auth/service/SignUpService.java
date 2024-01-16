package com.phupd202.auth.service;

import com.phupd202.auth.dto.SignUpDto;

public interface SignUpService {
    Boolean isExistedAccount(SignUpDto signUpDto);

    Boolean saveAccount(SignUpDto signupDto);
}
