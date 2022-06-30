package com.naukma.taskManager.service;

import com.naukma.taskManager.entity.CategoryEntity;
import com.naukma.taskManager.entity.TaskDto;
import com.naukma.taskManager.entity.TaskEntity;
import com.naukma.taskManager.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.naukma.taskManager.repository.CategoriesRepository;
import com.naukma.taskManager.repository.TasksRepository;
import com.naukma.taskManager.repository.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;

    @Transactional
    public TaskEntity createTask(TaskDto taskDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersRepository.findByEmail(email).orElse(null);
        CategoryEntity category = categoriesRepository.findByName(taskDto.getCategory()).orElse(null);

        TaskEntity taskEntity = new TaskEntity(taskDto,user,category);
        return tasksRepository.saveAndFlush(taskEntity);
    }

    @Transactional
    public TaskEntity getTaskById(long id) {
        return tasksRepository.findById(id).orElse(null);
    }


    @Transactional
    public List<TaskEntity> findAllTasks() {
        return tasksRepository.findAll();
    }

    @Transactional
    public List<TaskEntity> findAllTasksOfUser(UserEntity userEntity) {
        return tasksRepository.findByUser(userEntity);
    }

    @Transactional
    public TaskEntity updateTask(TaskDto taskDto) {
        UserEntity user = usersRepository.findById(taskDto.getUser()).orElse(null);
        CategoryEntity category = categoriesRepository.findByName(taskDto.getCategory()).orElse(null);
        TaskEntity taskEntity = new TaskEntity(taskDto,user,category);
        return  tasksRepository.saveAndFlush(taskEntity);
    }

    @Transactional
    public void deleteTask(TaskDto taskDto) {
        tasksRepository.deleteTaskEntityById(taskDto.getId());
    }
}
