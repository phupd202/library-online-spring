package com.phupd202.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phupd202.auth.entity.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long>{
    
}
