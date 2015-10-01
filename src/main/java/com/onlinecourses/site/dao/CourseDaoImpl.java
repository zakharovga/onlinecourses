package com.onlinecourses.site.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zakhar on 13.07.2015.
 */
@Component("coursesDao")
public class CourseDaoImpl implements CourseDao {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource jdbc) {
//        Locale.setDefault(Locale.ENGLISH);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc);
        this.jdbcTemplate = new JdbcTemplate(jdbc);
    }

    public List<Course> getAllCourses() {
        return namedParameterJdbcTemplate.query("select * from courses " +
                "left outer join subjects on courses.subject_code = subjects.subject_code " +
                "left outer join users on courses.teacher_id = users.user_id", new CourseRowMapper());
    }

    public Course getCourseById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject("select * from courses " +
                "left outer join subjects on courses.subject_code = subjects.subject_code " +
                "left outer join users on courses.teacher_id = users.user_id where courses.course_id = :id", params, new CourseRowMapper());
    }

    public boolean update(Course course) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", course.getId());
        params.addValue("startDate", course.getStartDate());
        params.addValue("description", course.getDescription());

        return namedParameterJdbcTemplate.update("update courses set start_date = to_date(:startDate, 'MM/DD/YYYY'), description = :description where course_id = :id", params) == 1;
    }

    public boolean save(Course course) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        int nextCourseId = jdbcTemplate.queryForObject("select courses_sequence.NEXTVAL from dual", Integer.class);

        params.addValue("id", nextCourseId);
        params.addValue("startDate", course.getStartDate());
        params.addValue("description", course.getDescription());

        return namedParameterJdbcTemplate.update("insert into courses (course_id, start_date, description) " +
                "values (:id, to_date(:startDate, 'MM/DD/YYYY'), :description)", params) == 1;
    }

    public boolean addSubjectToCourse(int id, Subject subject) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("code", subject.getCode());

        return namedParameterJdbcTemplate.update("update courses set subject_code = :code where course_id = :id", params) == 1;
    }

    public boolean addTeacherToCourse(int id, User teacher) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("teacherId", teacher.getId());

        return namedParameterJdbcTemplate.update("update courses set teacher_id = :teacherId where course_id = :id", params) == 1;
    }

    public boolean deleteCourse(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.update("delete from courses where course_id = :id", params) == 1;
    }

    public List<Course> getCoursesByStudentId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return namedParameterJdbcTemplate.query("select subjects.subject_code, subjects.subject_name, subjects.price, courses.start_date, users.user_id, users.email, users.first_name, users.last_name, courses.course_id, courses.description, course_user.mark from course_user " +
                "join courses on course_user.course_id = courses.course_id " +
                "join users on courses.teacher_id = users.user_id " +
                "join subjects on subjects.subject_code = courses.subject_code " +
                "where course_user.user_id = :id", params, new CourseRowMapper());
    }

    public List<Course> getNewCourses(int n) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("n", n);

        return namedParameterJdbcTemplate.query("select * from (select subjects.subject_code, subjects.subject_name, subjects.price, courses.start_date, users.user_id, users.email, users.first_name, users.last_name, courses.course_id, courses.description from courses " +
                "join users on courses.teacher_id = users.user_id " +
                "join subjects on subjects.subject_code = courses.subject_code " +
                "where courses.START_DATE > sysdate " +
                "order by courses.START_DATE desc) " +
                "where rownum <= :n " +
                "order by rownum desc", params, new CourseRowMapper());
    }

    public boolean addStudentToCourse(int id, int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);

        return namedParameterJdbcTemplate.update("insert into course_user (course_id, user_id) values (:id, :userId)", params) == 1;
    }

    public int studentsQuantity(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject("select count(*) from course_user where course_id = :id", params, Integer.class);
    }

    public Boolean isEnrolled(int id, int userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);

        return namedParameterJdbcTemplate.queryForObject("select count(*) from course_user where course_id = :id and user_id = :userId", params, Integer.class) != 0;
    }

    public List<Course> getCoursesByTeacherId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return namedParameterJdbcTemplate.query("select * from courses " +
                "join subjects on courses.subject_code = subjects.subject_code " +
                "join users on courses.teacher_id = users.user_id " +
                "where courses.teacher_id = :id", params, new CourseRowMapper());
    }

    public List<User> getStudentsByCourseId(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return namedParameterJdbcTemplate.query("select * from course_user " +
                "join users on course_user.user_id = users.user_id " +
                "where course_user.course_id = :id", params, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setEmail(resultSet.getString("email"));
                user.setLastName(resultSet.getString("last_name"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setMark(resultSet.getInt("mark"));

                return user;
            }
        });
    }

    public boolean rateStudent(int id, int userId, String mark) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("userId", userId);
        params.addValue("mark", mark);

        return namedParameterJdbcTemplate.update("update course_user set mark = :mark where course_id = :id and user_id = :userId", params) == 1;
    }
}