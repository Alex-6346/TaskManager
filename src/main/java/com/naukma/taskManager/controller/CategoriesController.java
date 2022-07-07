package com.naukma.taskManager.controller;


import com.naukma.taskManager.entity.*;
import com.naukma.taskManager.service.*;
import lombok.RequiredArgsConstructor;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoriesController {

    private final Validator validator;

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
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        categoryDto.setUser(user.getId());

//        final Set<ConstraintViolation<CategoryDto>> validationResult = validator.validate(categoryDto);
//        final List<String> errors = validationResult.stream()
//                .map(errorField -> "Field [" + errorField.getPropertyPath() + "] is invalid. Validation error: " + errorField.getMessage())
//                .collect(Collectors.toList());
//
//        if (!errors.isEmpty()) {
//            return ResponseEntity.badRequest()
//                    .body(errors);
//        }
//
//


//        if(categoriesService.namesByUser(user).contains(categoryDto.getName())) {
//            errors.add("User already have category with this name");
//            return ResponseEntity.badRequest()
//                    .body(errors);
//        }

        ResponseEntity<?> error = validateCategory(categoryDto, user);
        if(error != null){
            return error;
        }
        CategoryEntity categoryEntity = categoriesService.createCategory(categoryDto);
        categoryDto.setId(categoryEntity.getId());
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    private ResponseEntity<?> validateCategory(CategoryDto categoryDto, UserEntity user){
        final Set<ConstraintViolation<CategoryDto>> validationResult = validator.validate(categoryDto);
        final List<String> errors = validationResult.stream()
                .map(errorField -> "Field [" + errorField.getPropertyPath() + "] is invalid. Validation error: " + errorField.getMessage())
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(errors);
        }

        if(categoriesService.namesByUser(user).contains(categoryDto.getName())) {
            errors.add("User already have category with this name");
            return ResponseEntity.badRequest()
                    .body(errors);
        }

        return null;
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
    public ResponseEntity<?> updateCategory(Model model, @RequestBody CategoryDto categoryDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        categoryDto.setUser(user.getId());
        ResponseEntity<?> error = validateCategory(categoryDto, user);

        if(error != null){
            return error;
        }

        categoriesService.updateCategory(categoryDto);
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
        System.out.println("delete category: " + categoryDto);
        categoriesService.deleteCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    private CategoryDto convertToDto(CategoryEntity category) {
        return new CategoryDto(category);
    }

}