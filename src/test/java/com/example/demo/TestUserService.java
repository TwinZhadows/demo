package com.example.demo;

import com.example.demo.Entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)


public class TestUserService {

    @Autowired
    private UserService userService;
    @Order(1)
    @Test
    void testCreate() throws UserException {
        User user = userService.create(
                TestData.email,
                TestData.password,
                TestData.name
        );
        //not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        //check equals
        Assertions.assertEquals(TestData.email, user.getEmail());
        boolean isMatched = userService.matchPassword(TestData.password, user.getPassword());
        Assertions.assertTrue(isMatched);
        Assertions.assertEquals(TestData.name, user.getName());
    }
    @Order(2)
    @Test
    void testUpdate() throws UserException {
        Optional<User> opt = userService.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());
        User user = opt.get();
        User updatedUser = userService.updateName(user.getId(), TestUpdateData.name);
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(TestUpdateData.name, updatedUser.getName());
    }
    @Order(3)
    @Test
    void testDelete(){
        Optional<User> opt = userService.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());
        User user = opt.get();
        userService.deleteById(user.getId());
        Optional<User> optDel = userService.findByEmail(TestData.email);
        Assertions.assertTrue(optDel.isEmpty());
    }

    interface  TestData{
        String email = "chon2@test.com";
        String password = "1234";
        String name = "chon";
    }

    interface  TestUpdateData{
        String email = "chon@test.com";
        String password = "1234";
        String name = "chon2";
    }

}
