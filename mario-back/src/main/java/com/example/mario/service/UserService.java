package com.example.mario.service;

import com.example.mario.modal.User;

public interface UserService {
    User saveUser(User user);
    User getUser(Long id);
    User getHighScore();
    User getUserByUsername(String username);
}
