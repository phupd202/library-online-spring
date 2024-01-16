package com.phupd202.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phupd202.auth.entity.Account;
import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    List<Account> findByEmail(String email);

    List<Account> findByUsername(String username);
}
