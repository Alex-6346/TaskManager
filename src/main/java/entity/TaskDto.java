package entity;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime date;

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