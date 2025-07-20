package com.martin.journal.service;

import com.martin.journal.entity.User;
import com.martin.journal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {
    @Autowired
    public UserRepository userRepository;

    public void createUser(User newUser){
        userRepository.save(newUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
