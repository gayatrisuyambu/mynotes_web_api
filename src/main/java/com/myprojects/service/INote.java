package com.myprojects.service;

import com.myprojects.domain.Note;

public interface INote {
    Note createNote(Note note,int userId);

    Note updateNote(Note note, int userId);

    String deleteNote(int noteId, int userId);

    String shareNote(int noteId, int role, int sharedUserId);

    Note getNote(int noteId);
}
