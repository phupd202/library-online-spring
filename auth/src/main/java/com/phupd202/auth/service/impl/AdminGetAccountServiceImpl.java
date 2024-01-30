package com.phupd202.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.phupd202.auth.dto.AuthoritiesDto;
import com.phupd202.auth.entity.Authorities;
import com.phupd202.auth.mapper.AuthoritiesMapper;
import com.phupd202.auth.repository.AuthoritiesRepository;
import com.phupd202.auth.service.AdminGetAccountService;

@Service
public class AdminGetAccountServiceImpl implements AdminGetAccountService {
    // Constructor Injection
    private final AuthoritiesRepository authoritiesRepository;

    public AdminGetAccountServiceImpl(AuthoritiesRepository authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    // render all account on display
    @Override
    public List<AuthoritiesDto> findAllAccountService() {
        List<Authorities> authorities = authoritiesRepository.findAllAccount();

        return authorities.stream()
            .map(authority -> AuthoritiesMapper.mapToDTO(authority))
            .collect(Collectors.toList());

    }

    // delete account with sort delete
    @Override
    public Boolean deleteAccount(Long accountId) {
        Authorities authorities = authoritiesRepository.findByAccountId(accountId);

        // soft delele
        authorities.getAccount().setIsDeleted(true);
        return true;
    }

    // recovery account with soft delete
    @Override
    public Boolean recoveryAccount(Long accountId) {
        Authorities authorities = authoritiesRepository.findByAccountId(accountId);

        // recovery deleted
        authorities.getAccount().setIsDeleted(false);
        return true;
    }

}
