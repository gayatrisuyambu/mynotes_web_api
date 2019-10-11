package com.myprojects.dataccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DataAccessImpl implements IDataAccess {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> executeProcedure( String procedureName, List<SqlParameter> listOfDeclaredParameters, MapSqlParameterSource mapSqlParameterSource) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
        for(SqlParameter item : listOfDeclaredParameters){
            call.addDeclaredParameter(item);
        }
        Map<String, Object> execute = call.execute(mapSqlParameterSource);
        return execute;
    }

    @Override
    public Map<String, Object> executeProcedure(String procedureName){
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
        Map<String, Object> execute = call.execute();
        return execute;
    }

    @Override
    public Map<String, Object> executeProcedureWithResult(String procedureName, Map<String,RowMapper<?>> listOfDeclaredRowMapper, List<SqlParameter> listOfDeclaredParameters,  MapSqlParameterSource mapSqlParameterSource) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
        for(Map.Entry<String ,RowMapper<?>>  item: listOfDeclaredRowMapper.entrySet()){
            call.addDeclaredRowMapper(item.getKey(),item.getValue());
        }
        for(SqlParameter item : listOfDeclaredParameters){
            call.addDeclaredParameter(item);
        }
        Map<String, Object> execute = call.execute(mapSqlParameterSource);
        return execute;
    }

    @Override
    public Map<String, Object> executeProcedureWithResult(String procedureName,Map<String,RowMapper<?>> listOfDeclaredRowMapper) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName(procedureName);
        for(Map.Entry<String ,RowMapper<?>>  item: listOfDeclaredRowMapper.entrySet()){
            call.addDeclaredRowMapper(item.getKey(),item.getValue());
        }
        Map<String, Object> execute = call.execute();
        return execute;
    }
}
