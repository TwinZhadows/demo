package com.example.demo;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Social;
import com.example.demo.Entity.User;
import com.example.demo.exception.EmailException;
import com.example.demo.exception.UserException;
import com.example.demo.logic.EmailLogic;
import com.example.demo.service.AddressService;
import com.example.demo.service.SocialService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)


public class TestEmailService {

    @Autowired
    private EmailLogic emailLogic;

    @Test
    void testSendActivateEmail() throws EmailException {
        emailLogic.sendActivateUserEmail(TestData.email, TestData.name, TestData.token);
    }
    interface TestData{
        String email = "roonnapak.ch@gmail.com";
        String name = "Chon";
        String token = "!@ASD!#!@ASD!";
    }
}
