package com.example.demo.service;

import com.example.demo.Entity.Social;
import com.example.demo.Entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.repository.SocialRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SocialService {
    //DB connection for User

    private final SocialRepository repository;

    public SocialService(SocialRepository repository) {
        this.repository = repository;

    }

    public Social create(User user, String facebook, String line, String ig, String tiktok){
        Social entity = new Social();
        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setIg(ig);
        entity.setTiktok(tiktok);

        return repository.save(entity);
    }
    public Optional<User> findByUser(User user) {
        return repository.findByUser(user);
    }
}