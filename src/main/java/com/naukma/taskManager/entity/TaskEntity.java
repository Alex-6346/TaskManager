package com.naukma.taskManager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "task_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_due")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;

    @Column(name = "completed")
    private Boolean completed;
    
    @ManyToOne
    @JoinTable(name = "tasks_to_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserEntity user;

    @ManyToOne
    @JoinTable(name = "tasks_to_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private CategoryEntity category;

    public TaskEntity(TaskDto taskDto, UserEntity user, CategoryEntity category){
        if(!Objects.isNull(taskDto.getId())){
            this.id=taskDto.getId();
        }
        this.name=taskDto.getName();
        this.description=taskDto.getDescription();
        this.date=taskDto.getDate();
        this.user=user;
        this.completed=taskDto.getCompleted();
        this.category=category;
    }


}