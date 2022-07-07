package com.naukma.taskManager.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.naukma.taskManager.entity.CategoryEntity;
import com.naukma.taskManager.entity.TaskEntity;
import com.naukma.taskManager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoriesRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();
    List<CategoryEntity> findAllByUserId(long userId);
    CategoryEntity saveAndFlush(CategoryEntity categoryEntity);


    @Query("SELECT c.name FROM CategoryEntity c " +
            "where c.userId=:userId")
    List<String> namesByUser(long userId);

    @Modifying
    @Query("delete from CategoryEntity c where c.id=:id")
    void deleteCategory(@Param("id") Long id);

    Optional<CategoryEntity>findById(long id);
    Optional<CategoryEntity>findByName(String name);

    @Modifying
    @Query("update CategoryEntity c set c.name=:name, c.description=:description where c.id=:id")
    void updateCategory(@Param("id") long id, @Param("name") String name,  @Param("description") String description);


}