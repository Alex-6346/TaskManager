package com.naukma.taskManager.reposerv;

import com.naukma.taskManager.entity.*;
import com.naukma.taskManager.repository.TasksRepository;
import com.naukma.taskManager.service.TasksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TasksRepoServiceTests {
    @Autowired
    private TasksService service;
    @MockBean
    private TasksRepository repository;

    @Test
    public void findAllTasksTest() {
        when(repository.findAll()).thenReturn(Stream.of(new TaskEntity(), new TaskEntity()).collect(Collectors.toList()));
        assertEquals(2, service.findAllTasks().size());
    }
   @Test
    public void findByUserTests() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);


        CategoryEntity category=new CategoryEntity();
        TaskEntity dto=new TaskEntity(1, "read", "500 pages", LocalDate.of(2020, 1, 8),  false, ue, category);
       TaskEntity dto2=new TaskEntity(2, "write", "3k", LocalDate.of(2020, 1, 8),  true, ue, category);
        List<TaskEntity> opt = List.of(dto, dto2);
        when(repository.findByUser(ue)).thenReturn(opt);
        assertEquals(2, service.findAllTasksOfUser(ue).size());
    }

    @Test
    public void getTaskbyIdTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);


        CategoryEntity category=new CategoryEntity();
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2020, 1, 8),  true, ue, category);

        Optional<TaskEntity> opt = Optional.of(dto2);

        when(repository.findById(5))
                .thenReturn(opt);
        assertEquals(5, service.getTaskById(5).getId());
    }

    @Test
    public void deleteTaskTest() {
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);


        CategoryEntity category=new CategoryEntity();

        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2020, 1, 8),  true, ue, category);
TaskDto dto=new TaskDto(dto2);

        service.deleteTask(5);
        verify(repository, times(1)).deleteTask(dto.getId());
    }

    @Test
    public void getOverdueTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);


        CategoryEntity category=new CategoryEntity();
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2020, 10, 8),  false, ue, category);
        TaskEntity dto=new TaskEntity(2, "read", "500 pages", LocalDate.of(2021, 1, 8),false, ue, category);
        List<TaskEntity> opt = List.of(dto2, dto);

        when(repository.getOverdueTasks(ue, LocalDate.now()))
                .thenReturn(opt);
        assertEquals(2, service.findUserOverdueTasks(ue).size());
    }

    @Test
    public void findNotOverdueTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);


        CategoryEntity category=new CategoryEntity();
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2022, 9, 15),  false, ue, category);
        TaskEntity dto=new TaskEntity(2, "read", "500 pages", LocalDate.of(2021, 1, 8),false, ue, category);
        List<TaskEntity> opt = List.of(dto2, dto);

        when(repository.findNotOverdueTasks(ue, LocalDate.now()))
                .thenReturn(opt);
        assertEquals(opt, service.findUserNotOverdueTasks(ue));
    }
    @Test
    public void findTodayTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);

        CategoryEntity category=new CategoryEntity();
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2022, 7, 8),  false, ue, category);
        TaskEntity dto=new TaskEntity(2, "read", "500 pages", LocalDate.of(2021, 1, 8),false, ue, category);
        TaskEntity dto3=new TaskEntity(3, "fly", "200 miles", LocalDate.of(2021, 2, 3),true, ue, category);


        List<TaskEntity> comp = List.of(dto2, dto, dto3);
        when(repository.findTodayTasks(ue,LocalDate.now(), true ))
                .thenReturn(comp);
        assertEquals(comp, service.findUserTodayCompletedTasks(ue));

        List<TaskEntity> inc = List.of(dto2, dto, dto3);
        when(repository.findTodayTasks(ue,LocalDate.now(), false ))
                .thenReturn(inc);
        assertEquals(inc, service.findUserTodayIncompleteTasks(ue));


    }
    @Test
    public void findOverdueByCategoryTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);

        CategoryDto cat = new CategoryDto(1, "name", "descr", ue.getId());
        CategoryDto cat2 = new CategoryDto(2, "house", "make", ue.getId());
        CategoryEntity category=new CategoryEntity(cat, ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2022, 9, 8),  false, ue, category);
        TaskEntity dto=new TaskEntity(2, "read", "500 pages", LocalDate.of(2021, 1, 8),false, ue, category2);
        TaskEntity dto3=new TaskEntity(3, "fly", "200 miles", LocalDate.of(2021, 2, 3),true, ue, category);
        List<TaskEntity> inc = List.of(dto2, dto, dto3);

        when(repository.findOverdueTasksByCategory(ue, cat.getId(),LocalDate.now()))
                .thenReturn(inc);
        assertEquals(inc, service.findUserOverdueTasksByCategory(ue,cat.getId()));

    }
    @Test
    public void findNotOverdueByCategoryTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);

        CategoryDto cat = new CategoryDto(1, "name", "descr", ue.getId());
        CategoryDto cat2 = new CategoryDto(2, "house", "make", ue.getId());
        CategoryEntity category=new CategoryEntity(cat, ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2022, 9, 8),  false, ue, category);
        TaskEntity dto=new TaskEntity(2, "read", "500 pages", LocalDate.of(2021, 1, 8),false, ue, category2);
        TaskEntity dto3=new TaskEntity(3, "fly", "200 miles", LocalDate.of(2021, 2, 3),true, ue, category);
        List<TaskEntity> inc = List.of(dto2, dto, dto3);

        when(repository.findNotOverdueTasksByCategory(ue, cat2.getId(),LocalDate.now()))
                .thenReturn(inc);
        assertEquals(inc, service.findUserNotOverdueTasksByCategory(ue, cat2.getId()));

    }
    @Test
    public void findCompletedByDatesTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);

        CategoryDto cat = new CategoryDto(1, "name", "descr", ue.getId());
        CategoryDto cat2 = new CategoryDto(2, "house", "make", ue.getId());
        CategoryEntity category=new CategoryEntity(cat, ue.getId());
        CategoryEntity category2=new CategoryEntity(cat2, ue.getId());
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2022, 9, 8),  true, ue, category);
        TaskEntity dto=new TaskEntity(2, "read", "500 pages", LocalDate.of(2021, 1, 8),true, ue, category2);
        TaskEntity dto3=new TaskEntity(3, "fly", "200 miles", LocalDate.of(2018, 2, 3),true, ue, category);
        List<TaskEntity> inc = List.of(dto2, dto, dto3);

        when(repository.findCompletedTasksByDates(ue, LocalDate.of(2018, 1,1),LocalDate.of(2022, 12,12) ))
                .thenReturn(inc);
        assertEquals(inc, service.findUserCompletedTasksByDates(ue,LocalDate.of(2018, 1,1),LocalDate.of(2022, 12,12) ));

    }


}
