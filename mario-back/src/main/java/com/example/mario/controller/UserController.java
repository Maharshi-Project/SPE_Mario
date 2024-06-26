package com.example.mario.controller;

import com.example.mario.modal.User;
import com.example.mario.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin("http://localhost:3000")
@CrossOrigin("http://192.168.49.2:32000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.getUser(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/userByUsername/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/getHighScore")
    public ResponseEntity<User> getHighScore(){
        User user = userService.getHighScore();
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PutMapping("/updateUserScore")
    public ResponseEntity<User> updateUser(@RequestBody User newUserDetails) {
        try {
            User updatedUser = userService.updateUserByUsername(newUserDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/isPresent/{username}")
    public ResponseEntity<Boolean> isPresent(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if(user == null) {
            return ResponseEntity.ok(false);
        }
        else
            return ResponseEntity.ok(true);
    }

}
