package com.phupd202.auth.service;

import java.util.List;

import com.phupd202.auth.dto.AuthoritiesDto;

public interface AdminAuthoritiesService {
    List<AuthoritiesDto> findAllAccountService();

    // deleted Id by account
    Boolean deleteAccount(Long accountId);

    // recovery account by Id
    Boolean recoveryAccount(Long accountId);

    // edit account
    Boolean editAccount(String email, String phone, String role);
}
