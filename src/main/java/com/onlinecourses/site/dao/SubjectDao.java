package com.onlinecourses.site.dao;

import java.util.List;

/**
 * Created by zakharov_ga on 01.10.2015.
 */
public interface SubjectDao {
    List<Subject> getAllSubjects();

    boolean createSubject(Subject subject);

    Subject getSubjectByCode(String code);

    List<Subject> getSubjectsByTeacherId(int id);

    List<Subject> addSubjectToTeacher(int id, String code);

    List<Subject> getNotAddedSubjectsByTeacherId(int id);

    boolean update(Subject subject);

    List<User> getTeachersBySubjectCode(String code);

    List<User> getNotAddedTeachersBySubjectCode(String code);

    List<User> addTeacherToSubject(String code, int id);

    boolean exists(String code);

    boolean deleteSubject(String code);
}