package com.example.demo;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Social;
import com.example.demo.Entity.User;
import com.example.demo.exception.UserException;
import com.example.demo.service.AddressService;
import com.example.demo.service.SocialService;
import com.example.demo.service.UserService;
import com.sun.jdi.request.StepRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)


public class TestUserService {

    @Autowired
    private UserService userService;
    @Autowired
    private SocialService socialService;
    @Autowired
    private AddressService addressService;

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
    void testSocialCreate(){
        Optional<User> opt = userService.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());
        User user = opt.get();
        Social social = user.getSocial();
        Assertions.assertNull(social);
        social =  socialService.create(user, SocialTestCreateData.facebook, SocialTestCreateData.line, SocialTestCreateData.ig, SocialTestCreateData.tiktok);
        Assertions.assertNotNull(social);
        Assertions.assertEquals(social.getFacebook(), SocialTestCreateData.facebook);
    }
    @Order(4)
    @Test
    void testAddressCreate(){
        Optional<User> opt = userService.findByEmail(TestData.email);
        Assertions.assertTrue(opt.isPresent());
        User user = opt.get();
        List<Address> addresses = user.getAddresses();
        Assertions.assertTrue(addresses.isEmpty());
        Address address =  addressService.create(user, AddressTestData.line1, AddressTestData.line2, AddressTestData.zipcode);
        Assertions.assertNotNull(address);
        Assertions.assertEquals(AddressTestData.zipcode, address.getZipcode());

        Address address2 =  addressService.create(user, AddressTestData2.line1, AddressTestData2.line2, AddressTestData2.zipcode);
        Assertions.assertNotNull(address2);
        Assertions.assertEquals(AddressTestData2.zipcode, address2.getZipcode());

    }


    @Order(9)
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

    interface SocialTestCreateData{
        String facebook = "1231";
        String line = "";
        String ig = "";
        String tiktok = "12314";
    }

    interface AddressTestData{
        String line1 = "12345";
        String line2 = "2222";
        String zipcode = "90110";
    }

    interface AddressTestData2{
        String line1 = "xxxx";
        String line2 = "yyyy";
        String zipcode = "90000";
    }
}
