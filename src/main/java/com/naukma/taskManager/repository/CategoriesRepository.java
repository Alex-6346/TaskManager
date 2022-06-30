package com.naukma.taskManager.repository;

import java.util.List;
import java.util.Optional;

import com.naukma.taskManager.entity.CategoryEntity;
import com.naukma.taskManager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CategoriesRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();
    List<CategoryEntity> findAllByUser(UserEntity user);
    CategoryEntity saveAndFlush(CategoryEntity categoryEntity);


    @Query("SELECT c.name FROM CategoryEntity c " +
            "where c.user=:user")
    List<String> namesByUser(UserEntity user);

    Optional<CategoryEntity>findById(long id);
    Optional<CategoryEntity>findByName(String name);
    void deleteCategoryEntityById(long id);
}