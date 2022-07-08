package com.naukma.taskManager.repository;

import com.naukma.taskManager.entity.*;
import com.naukma.taskManager.service.TasksService;
import com.naukma.taskManager.service.UsersService;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TasksRepoTests {
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

        service.deleteTask(dto);
        verify(repository, times(1)).deleteTask(dto.getId());
    }

    @Test
    public void getOverdueTest(){
        UserDto user=new UserDto(1, "firstuser@gmail.com", "David Seaman","first123" );
        UserEntity ue=new UserEntity(user);


        CategoryEntity category=new CategoryEntity();
        TaskEntity dto2=new TaskEntity(5, "write", "3k", LocalDate.of(2020, 1, 8),  true, ue, category);


        Optional<TaskEntity> opt = Optional.of(dto2);

        when(repository.findById(5))
                .thenReturn(opt);
        assertEquals(5, service.getTaskById(5).getId());
    }
}
