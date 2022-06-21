package controller;

import entity.CategoryDto;
import entity.CategoryEntity;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CategoriesService;
import service.UsersService;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/add")
    public String addCategoryPage(Model model) {
        return "category-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoriesService.createCategory(categoryDto);
        categoryDto.setId(categoryEntity.getId());
        return  ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @GetMapping("/{id}")
    public String categoryPage(Model model, @PathVariable(value="id") long id){
        CategoryEntity categoryEntity = categoriesService.getCategoryById(id);
        CategoryDto categoryDto= new CategoryDto(categoryEntity);

        model.addAttribute("categoryInfo", categoryDto);
        return "category-page";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<CategoryDto> updateCategory(Model model, @RequestBody CategoryDto categoryDto){
        CategoryEntity categoryEntity = categoriesService.updateCategory(categoryDto);
        model.addAttribute("categoryInfo", categoryEntity);
        categoryDto.setId(categoryEntity.getId());
        return  ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

    @GetMapping("/view")
    public String viewCategories(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);

        List<CategoryEntity> categoriesList = categoriesService.findAllCategoriesByUser(user);
        model.addAttribute("categoriesInfo", categoriesList);

        return "categories-view";
    }


    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<CategoryDto> deleteCategory(Model model, @RequestBody CategoryDto categoryDto){
        categoriesService.deleteCategory(categoryDto);
        return  ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }

}