package com.example.service.impl;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.RegisterService;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl extends RegisterService {

    private final UserRepository userRepository;

    public RegisterServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        System.out.println("Registering user with username: " + user.getUserName());
        userRepository.save(user);
    }
}