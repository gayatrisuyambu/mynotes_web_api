package com.myprojects.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserUnAuthorizedException extends RuntimeException {
    public UserUnAuthorizedException(){
        super("User is Not authorized");
    }
}
