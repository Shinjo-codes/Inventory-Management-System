package com.example.servicee;

import java.util.List;

import com.example.entity.User;


public interface UserService {
    List<User> getAllUser();
    
    User saveUser(User user);

}