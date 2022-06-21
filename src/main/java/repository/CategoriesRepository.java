package repository;

import java.util.List;
import java.util.Optional;
import entity.CategoryEntity;
import entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriesRepository extends JpaRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();
    List<CategoryEntity> findAllByUser(UserEntity user);
    CategoryEntity saveAndFlush(CategoryEntity categoryEntity);

    Optional<CategoryEntity>findById(long id);
    Optional<CategoryEntity>findByName(String name);

    void deleteCategoryEntityById(long id);
}