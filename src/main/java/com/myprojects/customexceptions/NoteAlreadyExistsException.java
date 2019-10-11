package com.myprojects.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoteAlreadyExistsException extends RuntimeException {
    public NoteAlreadyExistsException() {
        super("Notes Already Exists");
    }
}
