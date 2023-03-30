package com.example.demo.api;

import com.example.demo.logic.ProductLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApi {

    @Autowired
    private ProductLogic product;

    @GetMapping("/product/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String id){
        String  response = product.getProductById(id);
        return ResponseEntity.ok(response);

    }

}
