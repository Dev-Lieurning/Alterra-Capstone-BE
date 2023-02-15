package com.capstone.reservation.repository;

import com.capstone.reservation.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    String FIND_BY_EMAIL = "select * from user where email = ?1";
    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    List<UserEntity> findByEmailUser(String email);

    Optional<UserEntity> findByEmail(String email);
}
