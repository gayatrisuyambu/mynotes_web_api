package com.myprojects.web;

import com.myprojects.domain.Note;
import com.myprojects.domain.User;
import com.myprojects.service.IUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private IUser iUser;

    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/users"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> createUserLogin(@RequestBody User user, HttpServletResponse sResponse) {
        String tokenGenerated = iUser.createUser(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",user.getUserName());
        jsonObject.put("emailId",user.getEmailId());
        jsonObject.put("token",tokenGenerated);
        Cookie cookie = new Cookie("mynotes_ui",jsonObject.toString());
        sResponse.addCookie(cookie);
        return ResponseEntity.ok("");
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"users/{userid}/notes"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> getCreatedNotes(@PathVariable int userid) {
        List<Note> noteList = iUser.getCreatedNotes(userid);
        return ResponseEntity.ok(noteList);
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"users/{userid}/sharednotes"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> getNotesSharedByUser(@PathVariable int userid) {
        List<Note> noteList = iUser.getNotesSharedByUser(userid);
        return ResponseEntity.ok(noteList);
    }

    @RequestMapping(
            method = {RequestMethod.GET},
            value = {"users/{userid}/notessharedwith"},
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<?> getNotesSharedWithUser(@PathVariable int userid) {
        List<Note> noteList = iUser.getNotesSaredWithUser(userid);
        return ResponseEntity.ok(noteList);
    }
}
