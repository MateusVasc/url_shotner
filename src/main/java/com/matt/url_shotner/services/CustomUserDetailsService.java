package com.matt.url_shotner.services;

import com.matt.url_shotner.entities.User;
import com.matt.url_shotner.enums.ExceptionType;
import com.matt.url_shotner.exceptions.InternalException;
import com.matt.url_shotner.repositories.UserRepository;
import com.matt.url_shotner.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new InternalException(ExceptionType.USER_NOT_FOUND_EXCEPTION));
        return CustomUserDetails.build(user);
    }
}
