package com.phupd202.auth.mapper;

import com.phupd202.auth.dto.AuthoritiesDto;
import com.phupd202.auth.entity.Authorities;

public class AuthoritiesMapper {
    public static AuthoritiesDto mapToDTO(Authorities authorities) {
        AuthoritiesDto authoritiesDto = new AuthoritiesDto();

        authoritiesDto.setAccountId(authorities.getAccount().getAccountId());
        authoritiesDto.setNameRole(authorities.getRole().getNameRole());
        authoritiesDto.setEmail(authorities.getAccount().getEmail());
        authoritiesDto.setPhone(authorities.getAccount().getPhone());
        authoritiesDto.setIsDeleted(authorities.getAccount().getIsDeleted());
        return authoritiesDto;
    }
}
