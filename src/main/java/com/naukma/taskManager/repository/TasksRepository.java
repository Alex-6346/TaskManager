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
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.parameters.P;


public interface TasksRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findAll();
    TaskEntity saveAndFlush(TaskEntity taskEntity);

    Optional<TaskEntity>findById(long id);
    List<TaskEntity> findByUser(UserEntity userEntity);

    @Modifying
    @Query("delete from TaskEntity t where t.id=:id")
    void deleteTask(@Param("id") Long id);

    @Modifying
    @Query("select t from TaskEntity t where t.user=:user and t.completed=false and t.date<:date")
    List<TaskEntity> getOverdueTasks(@Param("user") UserEntity userEntity, @Param("date") LocalDate date);

    @Modifying
    @Query("select t from TaskEntity t where t.user=:user and t.completed=false and t.date>=:date")
    List<TaskEntity> findNotOverdueTasks(@Param("user") UserEntity userEntity, @Param("date") LocalDate date);

    @Modifying
    @Query("select t from TaskEntity t where t.user=:user and t.completed=:completed and t.date=:date")
    List<TaskEntity> findTodayTasks(@Param("user") UserEntity userEntity, @Param("date") LocalDate date,
                                    @Param("completed") boolean completed);

    @Modifying
    @Query("select t from TaskEntity t where t.user=:user and t.completed=false " +
            "and t.category.id=:categoryID " + " and t.date<:date")
    List<TaskEntity> findOverdueTasksByCategory(@Param("user") UserEntity userEntity,
                                               @Param("categoryID") Long categoryID,
                                               @Param("date") LocalDate date);

    @Modifying
    @Query("select t from TaskEntity t where t.user=:user and t.completed=false " +
            "and t.category.id=:categoryID " + " and t.date>=:date")
    List<TaskEntity> findNotOverdueTasksByCategory(@Param("user") UserEntity userEntity,
                                                @Param("categoryID") Long categoryID,
                                                @Param("date") LocalDate date);


    @Modifying
    @Query("select t from TaskEntity t where t.user=:user and t.completed=true " +
            "and t.date >=:from " + " and t.date<=:to")
    List<TaskEntity> findCompletedTasksByDates(@Param("user") UserEntity userEntity,
                                                   @Param("from") LocalDate from,
                                                   @Param("to") LocalDate to);



    @Modifying
    @Query("update TaskEntity t set t.name=:name, t.category=:category, t.description=:description, t.completed=:completed, t.date=:date where t.id=:id")
    void updateTask(@Param("id") long id, @Param("name") String name, @Param("category")CategoryEntity category,
                                @Param("date") LocalDate date, @Param("description") String description, @Param("completed") boolean completed);



    void deleteTaskEntityById(long id);
}