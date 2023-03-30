package com.example.demo.repository;

import com.example.demo.Entity.Social;
import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface SocialRepository extends CrudRepository<Social, String> { //<Class name, type of primary key>
    Optional<User> findByUser(User user);
}
