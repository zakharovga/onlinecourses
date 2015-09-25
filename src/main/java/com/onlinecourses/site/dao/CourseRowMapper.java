package com.onlinecourses.site.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zakhar on 13.07.2015.
 */
public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet resultSet, int i) throws SQLException {

        Course course = new Course();

        User teacher = new User();
        teacher.setId(resultSet.getInt("user_id"));
        teacher.setEmail(resultSet.getString("email"));
        teacher.setLastName(resultSet.getString("last_name"));
        teacher.setFirstName(resultSet.getString("first_name"));

        Subject subject = new Subject();
        subject.setCode(resultSet.getString("subject_code"));
        subject.setName(resultSet.getString("subject_name"));
        subject.setPrice(resultSet.getDouble("price"));

        course.setId(resultSet.getInt("course_id"));
        course.setSubject(subject);
        course.setTeacher(teacher);
        course.setStartDate(resultSet.getString("start_date"));
        course.setDescription(resultSet.getString("description"));

        return course;
    }
}