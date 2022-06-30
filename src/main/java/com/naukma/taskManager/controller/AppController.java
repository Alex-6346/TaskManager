package com.naukma.taskManager.controller;


import com.naukma.taskManager.entity.CategoryDto;
import com.naukma.taskManager.entity.CategoryEntity;
import com.naukma.taskManager.entity.UserEntity;
import com.naukma.taskManager.service.CategoriesService;
import com.naukma.taskManager.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/")
    public String homePage(Model model) {
        System.out.println("Home");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        categoriesService.createCategory(new CategoryDto(1, "dsadsa", "dsfafadfsda", new Long(1)));
        System.out.println(categoriesService.findAllCategories());
        System.out.println(categoriesService.findAllCategoriesByUser(user));
        return "planned";
    }

}
