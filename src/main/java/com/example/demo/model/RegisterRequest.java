package com.example.demo.model;

import lombok.Data;

@Data
public class RegisterRequest
{
    private String name;
    private String email;
    private String password;

}

