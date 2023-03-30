package com.example.demo.repository;

import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
}
