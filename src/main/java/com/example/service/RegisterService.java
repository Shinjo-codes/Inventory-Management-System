package com.example.servicee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    public void register (User user) {
        userRepository.save(user);
    }

}