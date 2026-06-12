package com.apulia.library.controller;

import com.apulia.library.model.User;
import com.apulia.library.repository.UserRepository;
import com.apulia.library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.register(user);
        savedUser.setPassword(null);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
