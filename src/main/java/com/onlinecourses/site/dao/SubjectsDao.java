package com.onlinecourses.site.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

/**
 * Created by zakharov_ga on 08.07.2015.
 */
@Component("subjectsDao")
public class SubjectsDao {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource jdbc) {

        Locale.setDefault(Locale.ENGLISH);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc);
    }

    public List<Subject> getAllSubjects() {
        return namedParameterJdbcTemplate.query("select * from subjects", new SubjectsRowMapper());
    }

    public boolean createSubject(Subject subject) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("code", subject.getCode());
        params.addValue("name", subject.getName());
        params.addValue("price", subject.getPrice());
        params.addValue("description", subject.getDescription());

        return namedParameterJdbcTemplate.update("insert into subjects (subject_code, subject_name, price, description) " +
                "values (:code, :name, :price, :description)", params) == 1;
    }

    public Subject getSubjectByCode(String code) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", code);

        return namedParameterJdbcTemplate.queryForObject("select * from subjects where subject_code = :code", params, new SubjectsRowMapper());
    }

    public List<Subject> getSubjectsByTeacherId(int id) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.query("select * from subject_teacher, subjects where subjects.subject_code = subject_teacher.subject_code and subject_teacher.user_id = :id", params, new SubjectsRowMapper());
    }

    public List<Subject> addSubjectToTeacher(int id, String code) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("code", code);

        namedParameterJdbcTemplate.update("insert into subject_teacher (user_id, subject_code) " +
                " values (:id, :code)", params);
        return getSubjectsByTeacherId(id);
    }

    public List<Subject> getNotAddedSubjectsByTeacherId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.query("select * from SUBJECTS where subject_code not in (select subject_teacher.SUBJECT_CODE from subject_teacher, subjects where subjects.subject_code = subject_teacher.subject_code and subject_teacher.user_id = :id)", params, new SubjectsRowMapper());
    }

    public boolean update(Subject subject) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("code", subject.getCode());
        params.addValue("name", subject.getName());
        params.addValue("price", subject.getPrice());
        params.addValue("description", subject.getDescription());

        return namedParameterJdbcTemplate.update("update subjects set subject_name = :name, price = :price, description = :description where subject_code = :code", params) == 1;
    }

    public List<User> getTeachersBySubjectCode(String code) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", code);
        return namedParameterJdbcTemplate.query("select * from subject_teacher, users where users.user_id = subject_teacher.user_id and subject_teacher.subject_code = :code", params, new UsersRowMapper());
    }

    public List<User> getNotAddedTeachersBySubjectCode(String code) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", code);
        return namedParameterJdbcTemplate.query("select * from users, authorities\n" +
                "where authorities.user_id = users.user_id and authorities.authority = 'ROLE_TEACHER' and\n" +
                "users.user_id not in\n" +
                "    (select subject_teacher.user_id from subject_teacher, users\n" +
                "    where users.user_id = subject_teacher.user_id and subject_teacher.subject_code = :code)", params, new UsersRowMapper());
    }

    public List<User> addTeacherToSubject(String code, int id) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("code", code);

        namedParameterJdbcTemplate.update("insert into subject_teacher (user_id, subject_code) " +
                " values (:id, :code)", params);
        return getTeachersBySubjectCode(code);
    }

    public boolean exists(String code) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", code);

        return namedParameterJdbcTemplate.queryForObject("select count(*) from subjects where subject_code = :code", params, Integer.class) > 0;
    }

    public boolean deleteSubject(String code) {

        MapSqlParameterSource params = new MapSqlParameterSource("code", code);

        return namedParameterJdbcTemplate.update("delete from subjects where subject_code=:code", params) == 1;
    }
}