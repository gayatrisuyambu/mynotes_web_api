package com.myprojects.service;

import com.myprojects.customexceptions.NoteAlreadyExistsException;
import com.myprojects.customexceptions.NoteSharingException;
import com.myprojects.customexceptions.NotesNotFoundException;
import com.myprojects.dataccess.IDataAccess;
import com.myprojects.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

@Repository
public class NotesImpl implements INote {

    @Autowired
    private IDataAccess iDataAccess;

    @Override
    public Note createNote(Note note,int userId) throws NoteAlreadyExistsException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_notename", Types.VARCHAR));
        sqlParameterList.add(new SqlParameter("in_notedata", Types.BLOB));
        sqlParameterList.add(new SqlParameter("in_createdby", Types.INTEGER));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_notename", note.getNoteName());
        mapSqlParameterSource.addValue("in_notedata", note.getNoteData());
        mapSqlParameterSource.addValue("in_createdby", userId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_createnote", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        Note createdNote = new Note();
        if ((Integer)execute.get("out_noteid")!=-1) {
            createdNote.setNoteId((Integer)execute.get("out_noteid"));
            createdNote.setNoteData((String) execute.get("out_notesdata"));
            createdNote.setNoteName((String) execute.get("out_notename"));
            createdNote.setCreatedDate((Date) execute.get("out_createddate"));
            createdNote.setUpdatedDate((Date) execute.get("out_createddate"));
            return createdNote;
        } else {
            throw new NoteAlreadyExistsException();
        }
    }

    @Override
    public Note updateNote(Note note, int userId) throws NotesNotFoundException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_noteid", Types.INTEGER));
        sqlParameterList.add(new SqlParameter("in_notename", Types.VARCHAR));
        sqlParameterList.add(new SqlParameter("in_notedata", Types.BLOB));
        sqlParameterList.add(new SqlParameter("in_updatedby", Types.INTEGER));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_noteid", note.getNoteId());
        mapSqlParameterSource.addValue("in_notename", note.getNoteName());
        mapSqlParameterSource.addValue("in_notedata", note.getNoteData());
        mapSqlParameterSource.addValue("in_updatedby", userId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_updatenote", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        Note updatedNote = new Note();
        if ((Integer)execute.get("out_updatestatus")!=-1) {
            updatedNote.setNoteId((Integer)execute.get("out_noteid"));
            updatedNote.setNoteData((String) execute.get("out_notesdata"));
            updatedNote.setNoteName((String) execute.get("out_notename"));
            updatedNote.setCreatedDate((Date) execute.get("out_createdon"));
            updatedNote.setUpdatedDate((Date) execute.get("out_updateddate"));
            return updatedNote;
        } else {
            throw new NotesNotFoundException();
        }
    }

    @Override
    public String deleteNote(int noteId, int userId) throws NotesNotFoundException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_noteid", Types.INTEGER));
        sqlParameterList.add(new SqlParameter("in_userid", Types.INTEGER));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_noteid", noteId);
        mapSqlParameterSource.addValue("in_userid",userId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_deletenote", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        Note updatedNote = new Note();
        if ((Integer)execute.get("out_deletestatus")!=-1) {
            return "Deleted Successfully";
        } else {
            throw new NotesNotFoundException();
        }
    }

    @Override
    public String shareNote(int noteId, int role, int sharedUserId) throws NoteSharingException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_noteid", Types.INTEGER));
        sqlParameterList.add(new SqlParameter("in_userid", Types.INTEGER));
        sqlParameterList.add(new SqlParameter("in_roleid", Types.INTEGER));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_noteid", noteId);
        mapSqlParameterSource.addValue("in_userid",sharedUserId);
        mapSqlParameterSource.addValue("in_userid",role);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_sharenote", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        Note updatedNote = new Note();
        if ((Integer)execute.get("out_sharestatus")==1) {
            return "Shared Successfully";
        } else {
            throw new NoteSharingException();
        }
    }

    @Override
    public Note getNote(int noteId) throws NotesNotFoundException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_noteid", Types.INTEGER));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_noteid", noteId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_getnote", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        Note note = new Note();
        if (execute.get("out_noteid")!=null) {
            note.setNoteId((Integer)execute.get("out_noteid"));
            note.setNoteData((String) execute.get("out_notesdata"));
            note.setNoteName((String) execute.get("out_notename"));
            note.setCreatedDate((Date) execute.get("out_createdon"));
            note.setUpdatedDate((Date) execute.get("out_updateddate"));
            return note;
        } else {
            throw new NotesNotFoundException();
        }
    }
}
