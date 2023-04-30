package com.example.demo.service;

import com.example.demo.Entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class UserService {
    //DB connection for User

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String email, String password, String name, String token, Date tokenExpireDate) throws UserException {

        if (Objects.isNull(email)) {
            throw UserException.createEmailNull();
        }
        if (Objects.isNull(name)) {
            throw UserException.createNameNull();
        }
        if (Objects.isNull(password)) {
            throw UserException.createPasswordNull();
        }
        User entity = new User();
        entity.setEmail(email);
        entity.setName(name);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setActivateToken(token);
        entity.setTokenExpire(tokenExpireDate);
        return repository.save(entity);
    }

    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    //cache name = user , #id is variable name String id, #result == null means cache unless the result is null
    public Optional<User> findById(String id) {
        log.info("Load user from DB");
        return repository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @CachePut(value = "user", key = "#id") //update cache if user record is updated
    public User updateName(String id, String name) throws UserException {
        Optional<User> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        User user = opt.get();
        user.setName(name);
        return repository.save(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void deleteAll() {
        //delete all (records and caches)
    }

    @CacheEvict(value = "user", key = "#id")
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Optional<User> findByActivateToken(String token) {
        return repository.findByActivateToken(token);
    }


    public User update(User user) {
        return repository.save(user);
    }
}
