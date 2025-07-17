package com.mateusneubarth.bookshelf.service;

import com.mateusneubarth.bookshelf.model.User;
import com.mateusneubarth.bookshelf.repository.IUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String password) {
        User user = new User();
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setEmail(email);
        user.setName(name);
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public User login (String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null; // or throw an exception
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
