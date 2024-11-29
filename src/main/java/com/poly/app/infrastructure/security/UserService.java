package com.poly.app.infrastructure.security;


import com.poly.app.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

//    private final AccountRepository accountRepository;

    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        return null;
    }
}