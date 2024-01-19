package com.phupd202.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phupd202.auth.entity.Account;
import com.phupd202.auth.entity.Authorities;
import com.phupd202.auth.repository.AccountRepository;

@Service
public class JpaUserDetailService implements UserDetailsService  {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);

        if(account == null) {
            throw new UsernameNotFoundException("Can't load authentication information!!");
        }

        // get all of authorities of user
        List<Authorities> roleOfAccount = account.getRoleAccounts();
        
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        
        for (Authorities roleAccount : roleOfAccount) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleAccount.getRole().getNameRole()));
        }
        
        return new org.springframework.security.core.userdetails.User(
                                                        account.getEmail(), 
                                                        account.getPassword(), 
                                                        authorities);
    }
    
}
