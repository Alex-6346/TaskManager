package com.naukma.taskManager.controller;


import com.naukma.taskManager.entity.*;
import com.naukma.taskManager.service.*;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("")
    public String caategoriesPage(Model model) {
        return "categories";
    }

//    @GetMapping("/add")
//    public String addCategoryPage(Model model) {
//        return "category-add";
//    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        categoryDto.setUser(user.getId());
        CategoryEntity categoryEntity = categoriesService.createCategory(categoryDto);
        categoryDto.setId(categoryEntity.getId());
        return  ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }


//    @RequestMapping(value="/show/{id}", method=RequestMethod.GET)
    @GetMapping( "/{id}")
    public String categoryPage(Model model, @PathVariable(value="id") long id){
        model.addAttribute("category", new CategoryDto(categoriesService.getCategoryById(id)));
        return "category";
    }

    @GetMapping( "/info/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(value="id") Integer id){
        CategoryEntity categoryEntity = categoriesService.getCategoryById(id);
        CategoryDto categoryDto= new CategoryDto(categoryEntity);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<CategoryDto> updateCategory(Model model, @RequestBody CategoryDto categoryDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        categoryDto.setUser(user.getId());
        categoriesService.updateCategory(categoryDto);
        System.out.println("update category " + categoryDto);
        return  ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

//    @GetMapping("/view")
//    public String viewCategories(Model model) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = ((UserDetails) principal).getUsername();
//        UserEntity user = usersService.getUserByEmail(email);
//
//        List<CategoryEntity> categoriesList = categoriesService.findAllCategoriesByUser(user);
//        model.addAttribute("categoriesInfo", categoriesList);
//
//        return "categories-view";
//    }

    @GetMapping("/names")
    @ResponseBody
    public List<String> names(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return categoriesService.namesByUser(user);
    }

    @ResponseBody
    @GetMapping("/show-categories")
    public List<CategoryDto> showCategories(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return categoriesService.findAllCategoriesByUser(user).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<CategoryDto> deleteCategory(@RequestBody CategoryDto categoryDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        categoryDto.setUser(user.getId());
        categoriesService.deleteCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    private CategoryDto convertToDto(CategoryEntity category) {
        return new CategoryDto(category);
    }

}