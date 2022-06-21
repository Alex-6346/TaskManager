package service;

import entity.CategoryEntity;
import entity.CategoryDto;
import entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CategoriesRepository;
import repository.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;

    @Transactional
    public CategoryEntity createCategory(CategoryDto categoryDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersRepository.findByEmail(email).orElse(null);

        CategoryEntity categoryEntity = new CategoryEntity(categoryDto,user);
        return categoriesRepository.saveAndFlush(categoryEntity);
    }

    @Transactional
    public CategoryEntity getCategoryById(long id) {
        return categoriesRepository.findById(id).orElse(null);
    }


    @Transactional
    public List<CategoryEntity> findAllCategories() {
        return categoriesRepository.findAll();
    }
    @Transactional
    public List<CategoryEntity> findAllCategoriesByUser(UserEntity user) {
        return categoriesRepository.findAllByUser(user);
    }

    @Transactional
    public CategoryEntity updateCategory(CategoryDto categoryDto) {
        UserEntity user = usersRepository.findById(categoryDto.getUser()).orElse(null);
        CategoryEntity categoryEntity = new CategoryEntity(categoryDto,user);
        return  categoriesRepository.saveAndFlush(categoryEntity);
    }

    @Transactional
    public void deleteCategory(CategoryDto categoryDto) {
        categoriesRepository.deleteCategoryEntityById(categoryDto.getId());
    }
}
