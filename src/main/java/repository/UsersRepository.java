package repository;


import entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findAll();
    UserEntity saveAndFlush(UserEntity contactDto);

    Optional<UserEntity> findById(long id);
    Optional<UserEntity>findByEmail(String email);

    void deleteById(long id);

}
