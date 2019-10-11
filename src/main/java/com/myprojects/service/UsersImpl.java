package com.myprojects.service;

import com.myprojects.common.Validation;
import com.myprojects.customexceptions.*;
import com.myprojects.dataccess.IDataAccess;
import com.myprojects.domain.Note;
import com.myprojects.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.*;

@Repository
public class UsersImpl implements IUser{
    @Autowired
    private IDataAccess iDataAccess;

    @Override
    public String createUser(User user) {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_username", Types.VARCHAR));
        sqlParameterList.add(new SqlParameter("in_userpassword", Types.LONGVARCHAR));
        sqlParameterList.add(new SqlParameter("in_fname", Types.VARCHAR));
        sqlParameterList.add(new SqlParameter("in_lname", Types.VARCHAR));
        sqlParameterList.add(new SqlParameter("in_emailid", Types.VARCHAR));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_username", user.getUserName());
        mapSqlParameterSource.addValue("in_userpassword", Validation.getHash(user.getPassword()));
        mapSqlParameterSource.addValue("in_fname", user.getfName());
        mapSqlParameterSource.addValue("in_lname", user.getlName());
        mapSqlParameterSource.addValue("in_emailid", user.getEmailId());
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_createuser", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);

        if ((Integer)execute.get("out_userid")!=-1) {
            String generatedToken=Validation.getHash(user.getUserName()+user.getEmailId()+Validation.getHash(user.getPassword()));
            return generatedToken;
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public int validatingUser(String userName, String emailId, String token) {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("in_username", Types.VARCHAR));
        sqlParameterList.add(new SqlParameter("in_emailid", Types.VARCHAR));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("in_username", userName);
        mapSqlParameterSource.addValue("in_emailid", emailId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = iDataAccess.executeProcedureWithResult("api_getuser", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);

        if ((Integer)execute.get("out_userid")!=-1) {
            String generatedToken=Validation.getHash(userName+emailId+execute.get("out_userpassword"));
            if(generatedToken.equals(token))
                return (Integer)execute.get("out_userid");
            else
                throw new UserUnAuthorizedException();
        } else {
            throw new UserNotFoundException();
        }
    }


    public List<Note> getCreatedNotes(int userId) throws NotesNotFoundException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("userid", Types.INTEGER));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userid", userId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = this.iDataAccess.executeProcedureWithResult("api_getcreatednotes", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        List<Note> noteList = (List)execute.get("data");
        if (noteList != null && !noteList.isEmpty()) {
            return noteList;
        } else {
            throw new NotesNotFoundException();
        }
    }

    public List<Note> getNotesSharedByUser(int userId) throws NotesNotFoundException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("userid", 4));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userid", userId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = this.iDataAccess.executeProcedureWithResult("api_getsharednotes", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        List<Note> noteList = (List)execute.get("data");
        if (noteList != null && !noteList.isEmpty()) {
            return noteList;
        } else {
            throw new NotesNotFoundException();
        }
    }

    public List<Note> getNotesSaredWithUser(int userId) throws NotesNotFoundException {
        List<SqlParameter> sqlParameterList = new ArrayList();
        sqlParameterList.add(new SqlParameter("userid", 4));
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userid", userId);
        Map<String, RowMapper<?>> resultSetRowMapper = new HashMap();
        resultSetRowMapper.put("data", new BeanPropertyRowMapper(Note.class));
        Map<String, Object> execute = this.iDataAccess.executeProcedureWithResult("api_getnotesshared", resultSetRowMapper, sqlParameterList, mapSqlParameterSource);
        List<Note> noteList = (List)execute.get("data");
        if (noteList != null && !noteList.isEmpty()) {
            return noteList;
        } else {
            throw new NotesNotFoundException();
        }
    }
}
