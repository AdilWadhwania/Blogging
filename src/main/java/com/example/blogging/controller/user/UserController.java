package com.example.blogging.controller.user;

import com.example.blogging.model.User;
import com.example.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController
{

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/service")
    public String testService()
    {
        return "Service is up and running";
    }

    @GetMapping("/get")
    private List<User> getUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user)
    {
        userService.addUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam(name = "user") String user,@RequestParam(name = "password") String password)
    {
        if(userService.loginCheck(user,password))
        {
            return "Successfully log in";
        }
        else
            return "Failed to login";
    }
}
