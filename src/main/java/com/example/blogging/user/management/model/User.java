package com.example.blogging.user.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
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
    @Column( nullable = false)
    private String firstname;
    @Column( nullable = false)
    private String lastname;
    @Column( unique = true)
    private String username;
    @Column( nullable = false,unique = true)
    private String email;
    @Column( name = "user_password",nullable = false)
    private String password;
    @Column( nullable = false)
    private Timestamp created_time;
    @Column( nullable = false)
    private Timestamp updated_time;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    private List<Role> userRoles;

}
