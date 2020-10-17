package com.hackathon.controller;

import com.hackathon.entity.User;
import com.hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<User> create(@RequestParam String username) {
        try {
            int level = userRepository.getLevel(username);
            return new ResponseEntity<>(new User(username, level), HttpStatus.OK);
        } catch (Exception e) {
            userRepository.createUser(username);
            return new ResponseEntity<>(new User(username, 0), HttpStatus.OK);
        }
    }
}
