package com.myprojects.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoteSharingException extends RuntimeException {
    public NoteSharingException() {
        super("Not a valid sharing action");
    }
}
