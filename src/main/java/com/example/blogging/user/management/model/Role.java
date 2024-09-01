package com.example.blogging.user.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "roles",
        schema = "blogging"
)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "role_name",nullable = false)
    private String rolename;
    @JsonIgnore
    @ManyToMany(mappedBy = "userRoles")
    private List<User> users;

    public Role() {
    }

    public Role(int id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    public Role(String role_name, List<User> users) {
        this.rolename = role_name;
        this.users = users;
    }
}
