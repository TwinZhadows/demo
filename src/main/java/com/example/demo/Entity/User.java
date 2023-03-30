package com.example.demo.Entity;

//1to1Example

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

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
    @OneToOne(mappedBy = "user" ,orphanRemoval = true)//mapped to "user" field(named "m_user_id") in Social if deleted, delete in all connected tables
    private Social social;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)//dafault fecth type = LAZY, when user data is loaded, other one-to-many data are not loaded, fixed by fetch type EAGER
    private List<Address> addresses; //Object types should be List, Set
}
