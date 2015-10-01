package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.Course;
import com.onlinecourses.site.dao.CourseDao;
import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zakhar on 13.07.2015.
 */
@Service
public class DefaultCourseService implements CourseService {
    private static int QUANTITY = 100;

    @Autowired
    private CourseDao courseDao;

    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    public Course getCourseById(int id) {
        return courseDao.getCourseById(id);
    }

    public void update(Course course) {
        courseDao.update(course);
    }

    public void save(Course course) {
        courseDao.save(course);
    }

    public void deleteCourse(int id) {
        courseDao.deleteCourse(id);
    }

    @Transactional
    public void addSubjectAndTeacherToCourse(int id, Subject subject, User teacher) {
        if(subject != null) {
            courseDao.addSubjectToCourse(id, subject);
        }
        if(teacher != null) {
            courseDao.addTeacherToCourse(id, teacher);
        }
    }

    public List<Course> getCoursesByStudentId(int id) {
        return courseDao.getCoursesByStudentId(id);
    }

    public List<Course> getNewCourses(int n) {
        return courseDao.getNewCourses(n);
    }

    @Transactional
    public boolean addStudentToCourse(int id, int userId) {
        if (courseDao.studentsQuantity(id) >= this.QUANTITY) {
            return false;
        }
        else {
            courseDao.addStudentToCourse(id, userId);
            return true;
        }
    }

    public Boolean isEnrolled(int id, int userId) {
        return courseDao.isEnrolled(id, userId);
    }

    public List<Course> getCoursesByTeacherId(int id) {
        return courseDao.getCoursesByTeacherId(id);
    }

    public List<User> getStudentsByCourseId(int id) {
        return courseDao.getStudentsByCourseId(id);
    }

    public void rateStudent(int id, int userId, String mark) {
        courseDao.rateStudent(id, userId, mark);
    }
}