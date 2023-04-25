package com.example.demo.service;

import com.example.demo.Entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    //DB connection for User

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String email, String password, String name, String token) throws UserException {

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
        entity.setTokenExpire(setTokenExpireDate());
        return repository.save(entity);
    }

    private Date setTokenExpireDate() {
        //token expire in 30 mins
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MINUTE, 30);
        return calendar.getTime();
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User updateName(String id, String name) throws UserException {
        Optional<User> opt = repository.findById(id);
        if(opt.isEmpty()){
            throw UserException.notFound();
        }
        User user = opt.get();
        user.setName(name);
        return repository.save(user);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }
}
