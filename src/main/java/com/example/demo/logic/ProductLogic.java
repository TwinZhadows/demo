package com.example.demo.logic;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ProductLogic {

    public String getProductById(String id){
        //TODO: getdatafromDB
        return id;
    }

}
