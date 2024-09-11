package com.example.service;

import java.util.List;

import com.example.entity.User;


public interface UserService {
    List<User> getAllUser();
    
    User saveUser(User user);

}