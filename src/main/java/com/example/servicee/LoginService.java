package com.example.servicee;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;


    public User login(String userName) {
        return userRepository.findByUserName(userName)
                .stream()
                .findFirst()
                .orElse(null); // Handle the case where no user is found
    }
}
