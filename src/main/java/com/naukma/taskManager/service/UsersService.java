package com.naukma.taskManager.service;


import com.naukma.taskManager.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.naukma.taskManager.repository.*;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = new UserEntity(userDto);
        return usersRepository.saveAndFlush(userEntity);
    }

    @Transactional
    public UserEntity getUserById(long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Transactional
    public UserEntity getUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public List<UserEntity> findAllUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    public void deleteUser(UserEntity userEntity) { usersRepository.deleteById(userEntity.getId()); }
}