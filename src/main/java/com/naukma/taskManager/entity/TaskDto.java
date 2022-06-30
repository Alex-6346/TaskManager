package com.naukma.taskManager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskDto {

    private long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Name cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;

    private Long user;

    @NotNull(message = "Category cannot be null")
    private String category;


    public TaskDto(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.name = taskEntity.getName();
        this.description = taskEntity.getDescription();
        this.date = taskEntity.getDate();
        this.user = taskEntity.getUser().getId();
        this.category=taskEntity.getCategory().getName();
    }
}