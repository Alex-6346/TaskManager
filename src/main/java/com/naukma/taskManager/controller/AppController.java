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

    @GetMapping("/alltasks")
    public String homePage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);

//        if(categoriesService.findAllCategoriesByUser(user).isEmpty()) {
//            CategoryDto categoryDto = new CategoryDto();
//            categoryDto.setName("Default");
//            categoryDto.setDescription("test description");
//            categoryDto.setUser(user.getId());
//            categoriesService.createCategory(categoryDto);
//        }
        return "planned";
    }

    @GetMapping("/myday")
    public String myDayPage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return "myday";
    }

    @GetMapping("/completed")
    public String completed(){
        return "completed";
    }


}
