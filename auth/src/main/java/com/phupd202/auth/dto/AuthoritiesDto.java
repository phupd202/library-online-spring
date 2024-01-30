package com.phupd202.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthoritiesDto {
    private Long accountId;

    private String email;

    private String phone;

    private String nameRole;

    private Boolean isDeleted;
    
}
