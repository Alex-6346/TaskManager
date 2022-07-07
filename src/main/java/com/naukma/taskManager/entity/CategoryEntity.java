package com.naukma.taskManager.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories", uniqueConstraints = { @UniqueConstraint(columnNames = { "category_name", "user_id" }) })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name = "tasks_to_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<TaskEntity> tasks;

    @Column(name="user_id")
    private long userId;

   /* @ManyToOne
    @JoinTable(name = "categories_to_user",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserEntity user;*/

    public CategoryEntity(CategoryDto categoryDto, long userId){
        if(!Objects.isNull(categoryDto.getId())){
            this.id=categoryDto.getId();
        }
        System.out.println("User ID, create category entity: " + userId);
        this.name=categoryDto.getName();
        this.description=categoryDto.getDescription();
        this.userId=userId;
    }

}