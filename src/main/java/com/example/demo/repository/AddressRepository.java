package com.example.demo.repository;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.Social;
import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface AddressRepository extends CrudRepository<Address, String> { //<Class name, type of primary key>
    List<Address> findByUser(User user);

}
