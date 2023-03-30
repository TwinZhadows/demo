package com.example.demo.service;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Social;
import com.example.demo.Entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.SocialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    //DB connection for User

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;

    }

    public Address create(User user, String line1, String line2, String zipcode){
        Address entity = new Address();
        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);

        return repository.save(entity);
    }
    public List<Address> findByUser(User user) {
        return repository.findByUser(user);
    }
}