package com.matt.url_shotner.services;

import com.matt.url_shotner.dtos.request.CreateUserRequest;
import com.matt.url_shotner.entities.User;
import com.matt.url_shotner.enums.ExceptionType;
import com.matt.url_shotner.exceptions.InternalException;
import com.matt.url_shotner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(CreateUserRequest request) {
        if (this.userRepository.findByEmail(request.email()).isPresent()) {
            throw new InternalException(ExceptionType.USER_ALREADY_EXISTS);
        }

        User userToCreate = new User();
        userToCreate.setNickName(request.nickName());
        userToCreate.setEmail(request.email());
        userToCreate.setPassword(passwordEncoder.encode(request.password()));
        userToCreate.setCreatedAt(LocalDateTime.now());

        this.userRepository.save(userToCreate);
    }
}
