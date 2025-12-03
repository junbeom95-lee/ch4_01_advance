package com.example.advance.domain.user.repository;

import com.example.advance.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.username = :username")
    void updateUserEmailByJpql(@Param("username") String username, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :username")
    void deleteUserByJpql(@Param("username") String username);
}
