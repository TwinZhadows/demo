package com.example.demo.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "m_social")
public class Social extends BaseEntity {

    @Column(length = 120)
    private String facebook;
    //@JsonIgnore//dont send this field back to API in Json
    @Column(length = 120)
    private String line;
    @Column(length = 120)
    private String ig;
    @Column(length = 120)
    private String tiktok;

    @OneToOne
    @JoinColumn(name = "m_user_id", nullable = false)//Foreign k column named m_user_id
    private User user;
}
