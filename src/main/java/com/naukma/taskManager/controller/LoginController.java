package com.naukma.taskManager.controller;


import com.naukma.taskManager.entity.CategoryDto;
import com.naukma.taskManager.entity.UserDto;
import com.naukma.taskManager.entity.UserEntity;
import com.naukma.taskManager.service.CategoriesService;
import com.naukma.taskManager.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final Validator validator;


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
    public ResponseEntity<?> createUser(@RequestBody UserDto user){
        final Set<ConstraintViolation<UserDto>> validationResult = validator.validate(user);
        final List<String> errors = validationResult.stream()
                .map(errorField -> "Field [" + errorField.getPropertyPath() + "] is invalid. Validation error: " + errorField.getMessage())
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(errors);
        }

        UserEntity userEntity = usersService.registerUser(user);
        if(userEntity == null){
            errors.add("The user with this email is already registered");
            return ResponseEntity.badRequest()
                    .body(errors);
        }
        if(userEntity !=null) {
            user.setId(userEntity.getId());
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName("Default");
            categoryDto.setDescription("This is a default category");
            categoryDto.setUser(userEntity.getId());
            categoriesService.createCategory(categoryDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}