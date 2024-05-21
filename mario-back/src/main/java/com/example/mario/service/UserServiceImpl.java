package com.example.mario.service;

import com.example.mario.modal.User;
import com.example.mario.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user =  userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User getHighScore() {
        Optional<User> user = userRepository.findUserWithMaxScore();
        return user.orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user =  userRepository.findByUsername(username);
        return user.orElse(null);
    }
    @Override
    public User updateUserByUsername(User newUserDetails) {
        Optional<User> optionalUser = userRepository.findByUsername(newUserDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(newUserDetails.getUsername());
            if(optionalUser.get().getScore() < newUserDetails.getScore())
            {
                user.setScore(newUserDetails.getScore());
            }
            return userRepository.save(user);
        }
        else
        {
            return optionalUser.get();
        }
    }
}