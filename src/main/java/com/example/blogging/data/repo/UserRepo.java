package com.example.blogging.data.repo;

import com.example.blogging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "select * from users where user_name=?1;",nativeQuery = true)
    User getPasswordByName(String user_name);

}
