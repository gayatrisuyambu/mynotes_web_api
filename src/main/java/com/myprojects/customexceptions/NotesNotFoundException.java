package com.myprojects.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotesNotFoundException extends RuntimeException  {
    public NotesNotFoundException() {
        super("Notes Not Found");
    }
}
