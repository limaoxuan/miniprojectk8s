package com.max.account.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

//    @OneToMany(targetEntity = Address.class)
//    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<Address> addresses;
}
