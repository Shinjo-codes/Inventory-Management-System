package com.example.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.servicee.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;



    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }


    public User saveUser(User user) {
        System.out.println("Saving user with username: " + user.getUserName());
        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

}