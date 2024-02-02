package com.phupd202.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phupd202.auth.entity.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    @Query("SELECT au FROM Authorities au JOIN au.account ac JOIN au.role ro")
    List<Authorities> findAllAccount();

    @Query("SELECT au FROM Authorities au JOIN au.account ac WHERE ac.accountId = :accountId")
    Authorities findByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT au FROM Authorities au JOIN au.account ac WHERE ac.email = :email")
    Authorities findByAccountEmail(@Param("email") String email);
}
