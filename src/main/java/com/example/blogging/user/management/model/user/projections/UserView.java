package com.example.blogging.user.management.model.user.projections;

public interface UserView {

    String getusername();

    String getPassword();

    RoleView getuserRoles();
}
