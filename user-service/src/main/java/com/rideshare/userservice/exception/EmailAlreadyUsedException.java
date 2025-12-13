package com.rideshare.userservice.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException(String email) {
        super("This email address is already being used by another RideShare account.");
    }
}
