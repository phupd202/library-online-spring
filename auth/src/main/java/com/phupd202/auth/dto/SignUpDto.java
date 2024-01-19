package com.phupd202.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SignUpDto {
    private String email;

    private String phone;
    
    private String password;

    private String confirmPassword;
}
