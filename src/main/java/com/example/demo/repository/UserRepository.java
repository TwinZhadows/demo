package com.example.demo.repository;

import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String> { //<Class name, type of primary key>
    Optional<User> findByEmail(String email);

    Optional<User> findById(String email);

    Optional<User> findByActivateToken(String token);
}
