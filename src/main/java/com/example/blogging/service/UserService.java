package com.example.blogging.service;

import com.example.blogging.data.repo.UserRepo;
import com.example.blogging.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers()
    {
        try {
            return userRepo.findAll();
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public void addUser(User user)
    {
        userRepo.save(user);
    }

    public boolean loginCheck(String user,String userEntered)
    {
        System.out.println();
        User passwordInDB=userRepo.getPasswordByName(user);
        if(userEntered.equals(passwordInDB))
            return true;
        else
            return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.getPasswordByName(username);

        if(user==null)
        {
            throw new UsernameNotFoundException("No user found with email");
        }

        UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles("USER")
                .build();

        return userDetails;
    }

}
