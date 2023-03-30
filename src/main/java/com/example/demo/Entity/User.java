package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_user")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 60)
    private String email;
    //@JsonIgnore//dont send this field back to API in Json
    @Column(nullable = false, length = 120)
    private String password;
    @Column(nullable = false, length = 120)
    private String name;

}
