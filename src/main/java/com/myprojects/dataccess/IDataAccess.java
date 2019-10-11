package com.myprojects.dataccess;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;
import java.util.Map;

public interface IDataAccess {

    Map<String, Object> executeProcedure(String procedureName, List<SqlParameter> listOfDeclaredParameters, MapSqlParameterSource mapSqlParameterSource);

    Map<String, Object> executeProcedure(String procedureName);

    Map<String, Object> executeProcedureWithResult(String procedureName, Map<String, RowMapper<?>> listOfDeclaredRowMapper, List<SqlParameter> listOfDeclaredParameters, MapSqlParameterSource mapSqlParameterSource);

    Map<String, Object> executeProcedureWithResult(String procedureName, Map<String, RowMapper<?>> listOfDeclaredRowMapper);
}
