package com.onlinecourses.site.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zakharov_ga on 08.07.2015.
 */
public class SubjectsRowMapper implements RowMapper<Subject> {
    @Override
    public Subject mapRow(ResultSet resultSet, int i) throws SQLException {

        Subject subject = new Subject();

        subject.setCode(resultSet.getString("subject_code"));
        subject.setName(resultSet.getString("subject_name"));
        subject.setPrice(resultSet.getDouble("price"));
        subject.setDescription(resultSet.getString("description"));

        return subject;
    }
}