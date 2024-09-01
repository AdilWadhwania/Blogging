package com.example.blogging.user.management.service;

import com.example.blogging.user.management.model.user.UserRegisterRequest;
import com.example.blogging.user.management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserVerification
{

    private  UserRepo userRepo;
    private String verificationFailedCause;

    @Autowired
    public UserVerification(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String getVerificationFailedCause() {
        return verificationFailedCause;
    }

    public boolean verifyUserRequest(UserRegisterRequest userRequest) {
        // verify firstname and last name
        if(!verifyFirstAndLastName(userRequest.getFirstName(), userRequest.getLastName()))
        {
            return false;
        }
        else if(!verifyUserName(userRequest.getUserName()))
        {
            return false;
        }

        return true;
    }

    private  boolean verifyFirstAndLastName(String firstName, String lastName) {
        verificationFailedCause="";
        final String namePattern="^[a-zA-Z\s'-]+$";
        Pattern pattern=Pattern.compile(namePattern);
        Matcher matcher1=pattern.matcher(firstName);
        if(!matcher1.matches())
        {
            verificationFailedCause="Name should have only alphabets, spaces, hyphens and apostrophes :"+firstName;
            return false;
        }
        Matcher matcher2=pattern.matcher(firstName);
        if(!matcher2.matches())
        {
            verificationFailedCause="Name should have only alphabets, spaces, hyphens and apostrophes :"+lastName;
            return false;
        }
        return true;
    }

    private boolean verifyUserName(String userName){
        verificationFailedCause="";
        final String userPattern="^[a-zA-Z0-9-_]+$";
        Pattern pattern=Pattern.compile(userPattern);
        Matcher matcher=pattern.matcher(userName);
        if(!matcher.matches())
        {
            verificationFailedCause="User Name should have only Characters, alphabets, hyphens and underscores :"+userName;
            return false;
        }
        String nameFromDb= userRepo.findUniqueName(userName);
        if(nameFromDb!=null  )
        {
            if(!nameFromDb.equals(""))
            {
                verificationFailedCause="User Name :"+userName+" already exists please try with other user name";
                return false;
            }
        }
        return true;
    }



}
