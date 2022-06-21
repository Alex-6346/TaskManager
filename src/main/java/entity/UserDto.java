package entity;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

    private long id;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^\\s*(?:\\S\\s*){8,20}$", message = "Password should be between 8 and 20 characters")
    private String password;

    public UserDto(UserEntity userEntity){
        this.id= userEntity.getId();
        this.email=userEntity.getEmail();
        this.fullName=userEntity.getFullName();
        this.password=userEntity.getPassword();
    }

}
