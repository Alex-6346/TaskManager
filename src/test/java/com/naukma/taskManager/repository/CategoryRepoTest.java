package com.naukma.taskManager.repository;
import com.naukma.taskManager.entity.*;
import com.naukma.taskManager.service.CategoriesService;
import com.naukma.taskManager.service.TasksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepoTest {

    @Autowired
    private CategoriesService service;
    @MockBean
    private CategoriesRepository repository;

    @Test
    public void findAllTest() {
        when(repository.findAll()).thenReturn(Stream.of(new CategoryEntity(), new CategoryEntity()).collect(Collectors.toList()));
        assertEquals(2, service.findAllCategories().size());
    }

    @Test
    public void findCategoriesByUserTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);

        CategoryDto cat = new CategoryDto(1, "name", "descr", ue.getId());
        CategoryDto cat2 = new CategoryDto(2, "house", "make", ue.getId());
        CategoryEntity category=new CategoryEntity(cat, ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());
        List<CategoryEntity> cats = List.of(category, category2);
        when(repository.findCategoriesByUserID(ue.getId())).thenReturn(cats);
        assertEquals(cats, service.findAllCategoriesByUser(ue));
    }

    @Test
    public void namesByUserTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);
        UserDto user2=new UserDto(2, "first2user@gmail.com", "David2 Seaman","fir2st123" );
        UserEntity ue2=new UserEntity(user2);

        CategoryDto cat = new CategoryDto(1, "name", "descr", ue.getId());
        CategoryDto cat2 = new CategoryDto(2, "house", "make", ue2.getId());
        CategoryEntity category=new CategoryEntity(cat, ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue2.getId());
        List<String> cats = List.of(category.getName(), category2.getName());
        when(repository.namesByUser(ue.getId())).thenReturn(cats);
        List <String> random= Arrays.asList("house");

        assertEquals(cats, service.namesByUser(ue));
        assertNotEquals(cats, random);
    }

    @Test
    public void findByIdTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);

        CategoryDto cat = new CategoryDto(1, "name", "descr", ue.getId());
        CategoryDto cat2 = new CategoryDto(2, "house", "make", ue.getId());
        CategoryEntity category=new CategoryEntity(cat, ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());

        Optional<CategoryEntity> opt = Optional.of(category);

        when(repository.findById(1))
                .thenReturn(opt);
        assertEquals(1, service.getCategoryById(cat.getId()).getId());
    }
    /*@Test
    public void findByNameTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);
String name="name";
        CategoryDto cat2 = new CategoryDto(2, name, "make", ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());

        Optional<CategoryEntity> opt = Optional.of(category2);

        when(repository.findByName(name))
                .thenReturn(opt);
        assertEquals(2, service.getCategoryByName(name, ue.getId()).getId());
    }*/

    @Test
    public void findByNameAndUserTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);
        String name="name";
        CategoryDto cat2 = new CategoryDto(2, name, "make", ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());

        Optional<CategoryEntity> opt = Optional.of(category2);

        when(repository.findByNameAndUserId(name, ue.getId()))
                .thenReturn(opt);
        assertEquals(2, service.getCategoryByName(name, ue.getId()).getId());
    }

}
