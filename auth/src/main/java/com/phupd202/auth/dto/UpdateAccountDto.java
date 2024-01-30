package com.phupd202.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UpdateAccountDto {
    private Long accountId;
    private String action;
}
