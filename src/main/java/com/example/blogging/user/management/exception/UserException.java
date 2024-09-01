package com.example.blogging.user.management.exception;

public class UserException extends Exception{
    public UserException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Try again by correcting, "+super.getMessage();
    }


}
