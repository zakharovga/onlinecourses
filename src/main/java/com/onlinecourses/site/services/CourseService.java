package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.Course;
import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.User;

import java.util.List;

/**
 * Created by zakharov_ga on 01.10.2015.
 */
public interface CourseService {
    List<Course> getAllCourses();

    Course getCourseById(int id);

    void update(Course course);

    void save(Course course);

    void deleteCourse(int id);

    void addSubjectAndTeacherToCourse(int id, Subject subject, User teacher);

    List<Course> getCoursesByStudentId(int id);

    List<Course> getNewCourses(int n);

    boolean addStudentToCourse(int id, int userId);

    Boolean isEnrolled(int id, int userId);

    List<Course> getCoursesByTeacherId(int id);

    List<User> getStudentsByCourseId(int id);

    void rateStudent(int id, int userId, String mark);
}