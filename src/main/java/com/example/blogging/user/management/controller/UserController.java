package com.example.blogging.user.management.controller;

import com.example.blogging.user.management.model.user.UserLoginRequest;
import com.example.blogging.user.management.model.user.UserRegisterRequest;
import com.example.blogging.user.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController
{

    private UserService userService;

    private AuthenticationManager manager;


    @Autowired
    public UserController(UserService userService, AuthenticationManager manager) {
        this.userService = userService;
        this.manager = manager;
    }

    @GetMapping("/service")
    public String testService()
    {
        return "Service is up and running";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRequest) throws Exception {
        String username=userService.addUser(userRequest);
        if(username.contains("exception") || username.contains("verification"))
        {
            return ResponseEntity.badRequest().body(username);
        }
        return ResponseEntity.ok(username+" successfully registered ");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) throws Exception {
        try
        {
            manager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),loginRequest.getPassword()
                    ));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
        return ResponseEntity.ok(loginRequest.getUserName()+" successfully logged in");
    }
}
