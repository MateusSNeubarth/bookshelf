package com.mateusneubarth.bookshelf.controller;

import com.mateusneubarth.bookshelf.dto.request.LoginRequest;
import com.mateusneubarth.bookshelf.dto.request.RegistrationRequest;
import com.mateusneubarth.bookshelf.model.User;
import com.mateusneubarth.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody RegistrationRequest request) {
        User existingUser = userService.findByEmail(request.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
        }
        User user = userService.register(request.getName(), request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginRequest request) {
        try {
            userService.findByEmail(request.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = userService.login(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
