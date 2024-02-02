package com.phupd202.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.phupd202.auth.dto.AuthoritiesDto;
import com.phupd202.auth.entity.Account;
import com.phupd202.auth.entity.Authorities;
import com.phupd202.auth.entity.Role;
import com.phupd202.auth.mapper.AuthoritiesMapper;
import com.phupd202.auth.repository.AccountRepository;
import com.phupd202.auth.repository.AuthoritiesRepository;
import com.phupd202.auth.repository.RoleRepository;
import com.phupd202.auth.service.AdminAuthoritiesService;

@Service
public class AdminAuthoritiesServiceImpl implements AdminAuthoritiesService {
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthoritiesServiceImpl.class);

    // Constructor Injection
    private final AuthoritiesRepository authoritiesRepository;

    private final RoleRepository roleRepository;

    private final AccountRepository accountRepository;

    public AdminAuthoritiesServiceImpl(AuthoritiesRepository authoritiesRepository, RoleRepository roleRepository, AccountRepository accountRepository) {
        this.authoritiesRepository = authoritiesRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
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
    @Transactional
    public Boolean deleteAccount(Long accountId) {
        Authorities authorities = authoritiesRepository.findByAccountId(accountId);

        if (authorities == null) {
            return false;
        }

        // soft delele
        authorities.getAccount().setIsDeleted(true);
        authoritiesRepository.save(authorities);
        logger.warn("Có thao tác xoá mềm và lưu lại vào db trong AdminGetController");
        return true;
    }

    // recovery account with soft delete
    @Override
    @Transactional
    public Boolean recoveryAccount(Long accountId) {
        Authorities authorities = authoritiesRepository.findByAccountId(accountId);

        if (authorities == null) {
            return false;
        }

        // recovery deleted
        authorities.getAccount().setIsDeleted(false);
        authoritiesRepository.save(authorities);
        logger.warn("Có thao tác xoá mềm và lưu vào db trong AdminGetController");
        return true;
    }

    // Edit account with email, phone, role
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean editAccount(String email, String phone, String role) {
        Authorities authority = authoritiesRepository.findByAccountEmail(email);

        if(authority == null) {
            logger.warn("Không tìm thấy tài khoản: {}", email);
            return false;
        }
        // update info of account
        Account info = authority.getAccount();
        info.setPhone(phone);

        Role updatedRole = roleRepository.findRoleByNameRole(role);

        // save infor into database
        authority.setAccount(info);
        authority.setRole(updatedRole);
        try {
            authoritiesRepository.save(authority);
            return true;
        } catch(DataAccessException e) {
            logger.error("Có lỗi khi lưu dữ liệu vào bảng Authorities", e);
            return false;
        }
    }
}
