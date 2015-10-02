package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.User;

import java.util.List;

/**
 * Created by zakharov_ga on 01.10.2015.
 */
public interface SubjectService {
    List<Subject> getAllSubjects();

    Subject getSubjectByCode(String code);

    List<Subject> getSubjectsByTeacherId(int id);

    List<Subject> addSubjectToTeacher(int id, String code);

    List<Subject> getNotAddedSubjectsByTeacherId(int id);

    boolean exists(String code);

    void save(Subject subject, boolean edit);

    List<User> getTeachersBySubjectCode(String code);

    List<User> getNotAddedTeachersBySubjectCode(String code);

    List<User> addTeacherToSubject(String code, int id);

    void deleteSubject(String code);
}