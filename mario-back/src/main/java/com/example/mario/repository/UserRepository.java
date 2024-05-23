package com.example.mario.repository;

import com.example.mario.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user u WHERE u.score = (SELECT MAX(u2.score) FROM user u2) LIMIT 1", nativeQuery = true)
    Optional<User> findUserWithMaxScore();
    Optional<User> findByUsername(String username);
}
