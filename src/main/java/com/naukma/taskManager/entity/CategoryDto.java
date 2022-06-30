package com.naukma.taskManager.entity;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {

    private long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    private String description;

    private Long user;

   
    public CategoryDto(CategoryEntity categoryEntity){
        this.id= categoryEntity.getId();
        this.name=categoryEntity.getName();
        this.description=categoryEntity.getDescription();
        this.user= categoryEntity.getUser().getId();
    }

}
