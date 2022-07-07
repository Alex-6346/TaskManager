package com.naukma.taskManager.controller;


import com.naukma.taskManager.entity.CategoryDto;
import com.naukma.taskManager.entity.UserDto;
import com.naukma.taskManager.entity.UserEntity;
import com.naukma.taskManager.service.CategoriesService;
import com.naukma.taskManager.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private CategoriesService categoriesService;

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
    public UserDto createUser(@Valid @RequestBody UserDto user){
        UserEntity userEntity = usersService.registerUser(user);
        user.setId(userEntity.getId());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Default");
        categoryDto.setDescription("This is a default category");
        categoryDto.setUser(userEntity.getId());
        categoriesService.createCategory(categoryDto);
        System.out.println("Category dto" + categoryDto);
        return user;
    }
}