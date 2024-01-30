package com.phupd202.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phupd202.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findRoleByNameRole(String nameRole);
}
