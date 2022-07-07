package com.naukma.taskManager.service;

import com.naukma.taskManager.entity.CategoryEntity;
import com.naukma.taskManager.entity.CategoryDto;
import com.naukma.taskManager.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.naukma.taskManager.repository.CategoriesRepository;
import com.naukma.taskManager.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final CategoriesRepository categoriesRepository;

    @Transactional
    public CategoryEntity createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity(categoryDto,categoryDto.getUser());
        return categoriesRepository.saveAndFlush(categoryEntity);
    }

    @Transactional
    public CategoryEntity getCategoryById(long id) {
        return categoriesRepository.findById(id).orElse(null);
    }


    @Transactional
    public CategoryEntity getCategoryByName(String name, Long userID){
        return categoriesRepository.findByNameAndUserId(name, userID).get();
    }

    @Transactional
    public List<CategoryEntity> findAllCategories() {
        return categoriesRepository.findAll();
    }

    @Transactional
    public List<CategoryEntity> findAllCategoriesByUser(UserEntity user) {
//        return categoriesRepository.findAllByUserId(user.getId());
        return categoriesRepository.findCategoriesByUserID(user.getId());
    }

    @Transactional
    public List<String> namesByUser(UserEntity user){
        return categoriesRepository.namesByUser(user.getId());
    };



    @Transactional
    public void updateCategory(CategoryDto categoryDto) {
        categoriesRepository.updateCategory(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
    }

    @Transactional
    public void deleteCategory(CategoryDto categoryDto) {
        categoriesRepository.deleteCategory(categoryDto.getId());
    }
}
