package com.example.blogging.user.management.repo;

import com.example.blogging.user.management.model.User;
import com.example.blogging.user.management.model.user.projections.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "select * from users where user_name=?1;",nativeQuery = true)
    User getPasswordByName(String user_name);

    UserView findByUsername(String username);

    @Query(nativeQuery = true,value = "select username from users where username=:name")
    String findUniqueName(@Param("name") String username);

}
