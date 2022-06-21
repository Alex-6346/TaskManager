package controller;

import entity.UserDto;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.UsersService;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;


    @GetMapping("/{id}")
    public String userPage(Model model, @PathVariable(value="id") long id){
        UserEntity userEntity = usersService.getUserById(id);
        UserDto userDto = new UserDto(userEntity);
        model.addAttribute("userInfo", userDto);
        return "user-page";
    }

    @PostMapping("/delete")
    @ResponseBody
    public  ResponseEntity<UserDto> deleteUsersPage(@RequestBody long id) {
        UserEntity userEntity = usersService.getUserById(id);
        usersService.deleteUser(userEntity);
        return  ResponseEntity.status(HttpStatus.OK).body(new UserDto(userEntity));
    }



}
