package com.myprojects.service;

import com.myprojects.customexceptions.NotesNotFoundException;
import com.myprojects.domain.Note;
import com.myprojects.domain.User;

import java.util.List;

public interface IUser {
    String createUser(User user);

    int validatingUser(String userName,String emailId,String token);

    List<Note> getCreatedNotes(int userId) throws NotesNotFoundException;

    List<Note> getNotesSharedByUser(int userId) throws NotesNotFoundException;

    List<Note> getNotesSaredWithUser(int userId) throws NotesNotFoundException;
}
