package com.myprojects.web;

import com.myprojects.domain.Note;
import com.myprojects.domain.NoteShareBody;
import com.myprojects.service.INote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotesController {
    @Autowired
    INote iNote;

    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/notes"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> createNote(@RequestBody Note note,@RequestAttribute("userid") int userid) {
        Note createdNote = iNote.createNote(note,userid);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"/notes/{note_id}"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> getNote(@PathVariable int note_id) {
        Note note = iNote.getNote(note_id);
        return ResponseEntity.ok(note);
    }

    @RequestMapping(
            method = {RequestMethod.PUT},
            value = {"/notes/{note_id}"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> updateNote(@PathVariable int note_id,@RequestBody Note note,@RequestAttribute("userid") int userid) {
        Note updateNote = iNote.updateNote(note,userid);
        return ResponseEntity.ok(updateNote);
    }

    @RequestMapping(
            method = {RequestMethod.DELETE},
            value = {"/notes/{note_id}"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> deleteNote(@PathVariable int note_id,@RequestAttribute("userid") int userid) {
        String response = iNote.deleteNote(note_id,userid);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            method = {RequestMethod.PUT},
            value = {"/notes/{note_id}/share"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?>  shareNote(@PathVariable int note_id,@RequestAttribute("userid") int userid,@RequestBody NoteShareBody noteShareBody) {
        String response = iNote.shareNote(noteShareBody.getNoteId(),noteShareBody.getRoleId(),noteShareBody.getUserId());
        return ResponseEntity.ok(response);
    }

}
