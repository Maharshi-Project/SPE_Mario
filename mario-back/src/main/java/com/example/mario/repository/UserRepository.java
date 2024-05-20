package com.example.mario.repository;

import com.example.mario.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.score = (SELECT MAX(u2.score) FROM User u2)")
    Optional<User> findUserWithMaxScore();
    Optional<User> findByUsername(String username);
}
