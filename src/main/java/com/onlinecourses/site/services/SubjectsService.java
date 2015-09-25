package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.SubjectsDao;
import com.onlinecourses.site.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zakharov_ga on 08.07.2015.
 */
@Component("subjectsService")
public class SubjectsService {

    private SubjectsDao subjectsDao;

    @Autowired
    public void setSubjectsDao(SubjectsDao subjectsDao) {
        this.subjectsDao = subjectsDao;
    }

    public List<Subject> getAllSubjects() {
        return subjectsDao.getAllSubjects();
    }

    public Subject getSubjectByCode(String code) {
        return subjectsDao.getSubjectByCode(code);
    }

    public List<Subject> getSubjectsByTeacherId(int id) {
        return subjectsDao.getSubjectsByTeacherId(id);
    }

    public List<Subject> addSubjectToTeacher(int id, String code) {
        return subjectsDao.addSubjectToTeacher(id, code);
    }

    public List<Subject> getNotAddedSubjectsByTeacherId(int id) {
        return subjectsDao.getNotAddedSubjectsByTeacherId(id);
    }

    public boolean exists(String code) {
        return subjectsDao.exists(code);
    }

    public void save(Subject subject, boolean edit) {
        if(!edit) {
            subjectsDao.createSubject(subject);
        }
        else {
            System.out.println(subject.getPrice());
            subjectsDao.update(subject);
        }
    }

    public List<User> getTeachersBySubjectCode(String code) {
        return subjectsDao.getTeachersBySubjectCode(code);
    }

    public List<User> getNotAddedTeachersBySubjectCode(String code) {
        return subjectsDao.getNotAddedTeachersBySubjectCode(code);
    }

    public List<User> addTeacherToSubject(String code, int id) {
        return subjectsDao.addTeacherToSubject(code, id);
    }

    public void deleteSubject(String code) {
        subjectsDao.deleteSubject(code);
    }
}