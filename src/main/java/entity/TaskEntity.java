package entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime date;

    @ManyToOne
    @JoinTable(name = "tasks_to_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserEntity user;

    public TaskEntity(TaskDto taskDto, UserEntity user){
        if(!Objects.isNull(taskDto.getId())){
            this.id=taskDto.getId();
        }
        this.name=taskDto.getName();
        this.description=taskDto.getDescription();
        this.date=taskDto.getDate();
        this.user=user;
    }


}