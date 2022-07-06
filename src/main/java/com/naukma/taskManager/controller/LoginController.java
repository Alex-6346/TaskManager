package com.naukma.taskManager.controller;


import com.naukma.taskManager.entity.UserDto;
import com.naukma.taskManager.entity.UserEntity;
import com.naukma.taskManager.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String loginPage() {return "login";};

    @GetMapping("/register")
    public String registerPage(Model model){
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public UserEntity createUser(@Valid @RequestBody UserDto user){
        System.out.println(user);
        return  usersService.registerUser(user);
    }



}