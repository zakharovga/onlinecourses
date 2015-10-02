package com.onlinecourses.site.dao;

import java.util.List;

/**
 * Created by zakharov_ga on 01.10.2015.
 */
public interface CourseDao {
    List<Course> getAllCourses();

    Course getCourseById(int id);

    boolean update(Course course);

    boolean save(Course course);

    boolean addSubjectToCourse(int id, Subject subject);

    boolean addTeacherToCourse(int id, User teacher);

    boolean deleteCourse(int id);

    List<Course> getCoursesByStudentId(int id);

    List<Course> getNewCourses(int n);

    boolean addStudentToCourse(int id, int userId);

    int studentsQuantity(int id);

    Boolean isEnrolled(int id, int userId);

    List<Course> getCoursesByTeacherId(int id);

    List<User> getStudentsByCourseId(int id);

    boolean rateStudent(int id, int userId, String mark);
}