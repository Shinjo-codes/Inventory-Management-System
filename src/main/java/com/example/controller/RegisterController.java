package com.example.controller;


import com.example.entity.User;
import com.example.servicee.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;


    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        System.out.println("Accessed /register endpoint");
        model.addAttribute("user", new User());
        return "register";
    }

    
    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        
        //logging the user data (prints username and password to the console)
        System.out.println("User Name: " + user.getUserName());
        System.out.println("Password: " + user.getPassword());
        
        registerService.register(user);
        return "redirect:/add-product";
    }
}
