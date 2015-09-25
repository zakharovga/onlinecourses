package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zakhar on 13.07.2015.
 */
@Component("coursesService")
public class CoursesService {
    private static int QUANTITY = 100;
    private CoursesDao coursesDao;
    private UsersDao usersDao;

    @Autowired
    public void setCoursesDao(CoursesDao coursesDao) {
        this.coursesDao = coursesDao;
    }

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public List<Course> getAllCourses() {
        return coursesDao.getAllCourses();
    }

    public Course getCourseById(int id) {
        return coursesDao.getCourseById(id);
    }

    public void update(Course course) {
        coursesDao.update(course);
    }

    public void save(Course course) {
        coursesDao.save(course);
    }

    public void deleteCourse(int id) {
        coursesDao.deleteCourse(id);
    }

    @Transactional
    public void addSubjectAndTeacherToCourse(int id, Subject subject, User teacher) {
        if(subject != null) {
            coursesDao.addSubjectToCourse(id, subject);
        }
        if(teacher != null) {
            coursesDao.addTeacherToCourse(id, teacher);
        }
    }

    public List<Course> getCoursesByStudentId(int id) {
        return coursesDao.getCoursesByStudentId(id);
    }

    public List<Course> getNewCourses(int n) {
        return coursesDao.getNewCourses(n);
    }

    @Transactional
    public boolean addStudentToCourse(int id, int userId) {
        if(coursesDao.studentsQuantity(id) >= this.QUANTITY) {
            return false;
        }
        else {
            coursesDao.addStudentToCourse(id, userId);
            return true;
        }
    }

    public Boolean isEnrolled(int id, int userId) {
        return coursesDao.isEnrolled(id, userId);
    }

    public List<Course> getCoursesByTeacherId(int id) {
        return coursesDao.getCoursesByTeacherId(id);
    }

    public List<User> getStudentsByCourseId(int id) {
        return coursesDao.getStudentsByCourseId(id);
    }

    public void rateStudent(int id, int userId, String mark) {
        coursesDao.rateStudent(id, userId, mark);
    }
}