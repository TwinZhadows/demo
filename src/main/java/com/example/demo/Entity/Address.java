package com.example.demo.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

//Many to One example
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_address")
public class Address extends BaseEntity implements Serializable {//implement Serializable for caching

    @Column(length = 120)
    private String line1;
    //@JsonIgnore//dont send this field back to API in Json
    @Column(length = 120)
    private String line2;
    @Column(length = 120)
    private String zipcode;


    @ManyToOne
    @JoinColumn(name = "m_user_id", nullable = false)//Foreign k column named m_user_id
    private User user;
}
