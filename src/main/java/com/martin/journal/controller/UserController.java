package com.martin.journal.controller;

import com.martin.journal.entity.User;
import com.martin.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        try {
            userService.createUser(newUser);
            return ResponseEntity.accepted().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        try {
            List<User> allUsers = userService.getAllUsers();
            return new ResponseEntity<List<User>>(allUsers,HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
