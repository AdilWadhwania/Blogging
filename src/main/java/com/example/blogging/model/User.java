package com.example.blogging.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table
        (
                name = "Users",
                schema = "Blogging"
        )
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column( nullable = false,unique = true)
    private String email;
    @Column( name = "user_password",nullable = false)
    private String password;

}
