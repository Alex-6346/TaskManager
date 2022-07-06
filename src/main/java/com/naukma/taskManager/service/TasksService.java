package com.naukma.taskManager.service;

import com.naukma.taskManager.entity.CategoryEntity;
import com.naukma.taskManager.entity.TaskDto;
import com.naukma.taskManager.entity.TaskEntity;
import com.naukma.taskManager.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.naukma.taskManager.repository.CategoriesRepository;
import com.naukma.taskManager.repository.TasksRepository;
import com.naukma.taskManager.repository.UsersRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksService {

    @Autowired
    private final TasksRepository tasksRepository;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
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
    public List<TaskEntity> findUserNotOverdueTasks(UserEntity userEntity) {
        return tasksRepository.findNotOverdueTasks(userEntity, LocalDate.now());
    }

    @Transactional
    public List<TaskEntity> findUserTodayIncompleteTasks(UserEntity userEntity){
        return tasksRepository.findTodayTasks(userEntity, LocalDate.now(), false);
    }

    @Transactional
    public List<TaskEntity> findUserTodayCompletedTasks(UserEntity userEntity){
        return tasksRepository.findTodayTasks(userEntity, LocalDate.now(), true);
    }

    @Transactional
    public List<TaskEntity> findUserOverdueTasksByCategory(UserEntity userEntity, Long categoryID){
        return tasksRepository.findOverdueTasksByCategory(userEntity, categoryID, LocalDate.now());
    }

    @Transactional
    public List<TaskEntity> findUserNotOverdueTasksByCategory(UserEntity userEntity, Long categoryID){
        return tasksRepository.findNotOverdueTasksByCategory(userEntity, categoryID, LocalDate.now());
    }

    @Transactional
    public List<TaskEntity> findUserCompletedTasksByDates(UserEntity userEntity, LocalDate from, LocalDate to){
        return tasksRepository.findCompletedTasksByDates(userEntity, from, to);
    }


    @Transactional
    public List<TaskEntity> findUserOverdueTasks(UserEntity userEntity) {
        return tasksRepository.getOverdueTasks(userEntity, LocalDate.now());
    }

    @Transactional
    public void updateTask(TaskDto taskDto) {
//        UserEntity user = usersRepository.findById(taskDto.getUser()).orElse(null);
        CategoryEntity category = categoriesRepository.findByName(taskDto.getCategory()).orElse(null);
        tasksRepository.updateTask(taskDto.getId(), taskDto.getName(),
                category, taskDto.getDate(), taskDto.getDescription(), taskDto.getCompleted());
    }



    @Transactional
    public void deleteTask(TaskDto taskDto) {
        tasksRepository.deleteTask(taskDto.getId());
//        tasksRepository.deleteTaskEntityById(taskDto.getId());
    }
}
