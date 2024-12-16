package com.example.project1.Repository;

import com.example.project1.Entity.User;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findFirstByUsername(String username);
    User findFirstByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE username = ?1", nativeQuery = true)
    int deleteByUsername(String username);

    List<User> findAllByOrderByUserIDAsc();
}
