package entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "tasks_to_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<TaskEntity> tasks;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "categories_to_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryEntity> categories;

    public UserEntity(UserDto userDto){
        if(!Objects.isNull(userDto.getId())){
            this.id=userDto.getId();
        }
        this.email=userDto.getEmail();
        this.fullName=userDto.getFullName();
        this.password=userDto.getPassword();
    }

}
