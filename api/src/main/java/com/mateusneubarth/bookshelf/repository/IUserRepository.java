package com.mateusneubarth.bookshelf.repository;

import com.mateusneubarth.bookshelf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
