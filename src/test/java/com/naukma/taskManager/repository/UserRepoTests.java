package com.naukma.taskManager.repository;

import com.naukma.taskManager.entity.UserDto;
import com.naukma.taskManager.entity.UserEntity;
import com.naukma.taskManager.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTests {
    @Autowired
    private UsersService service;
    @MockBean
    private UsersRepository repository;

    @Test
    public void getUsersTest() {
        when(repository.findAll()).thenReturn(Stream.of(new UserEntity(), new UserEntity()).collect(Collectors.toList()));
        assertEquals(2, service.findAllUsers().size());
    }
    @Test
    public void getUserbyAddressTest() {
        String address = "firstuser@gmail.com";
        UserDto user=new UserDto(1, address, "David Seaman","first123" );
        Optional<UserEntity> opt = Optional.of(new UserEntity(user));
        when(repository.findByEmail(address))
                .thenReturn(opt);
        assertEquals(address, service.getUserByEmail(address).getEmail());
    }
    @Test
    public void getUserbyIdTest() {

        UserDto user=new UserDto(2, "firstuser@gmail.com", "David Seaman","first123" );
        Optional<UserEntity> opt = Optional.of(new UserEntity(user));
        when(repository.findById(2))
                .thenReturn(opt);
        assertEquals(2, service.getUserById(2).getId());
    }
    @Test
    public void deleteUserTest() {
        UserDto user=new UserDto(3, "seconduser@gmail.com", "Peter Schmeichel","second123" );
        UserEntity ue=new UserEntity(user);
        service.deleteUser(ue);
        verify(repository, times(1)).deleteById(3);
    }


}
