package com.naukma.taskManager.repository;

import java.util.List;
import java.util.Optional;

import com.naukma.taskManager.entity.TaskEntity;
import com.naukma.taskManager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TasksRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findAll();
    TaskEntity saveAndFlush(TaskEntity taskEntity);

    Optional<TaskEntity>findById(long id);
    List<TaskEntity> findByUser(UserEntity userEntity);

    void deleteTaskEntityById(long id);


}