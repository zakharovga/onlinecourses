package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.Subject;
import com.onlinecourses.site.dao.SubjectDao;
import com.onlinecourses.site.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zakharov_ga on 08.07.2015.
 */
@Service
public class DefaultSubjectService implements SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    public List<Subject> getAllSubjects() {
        return subjectDao.getAllSubjects();
    }

    public Subject getSubjectByCode(String code) {
        return subjectDao.getSubjectByCode(code);
    }

    public List<Subject> getSubjectsByTeacherId(int id) {
        return subjectDao.getSubjectsByTeacherId(id);
    }

    public List<Subject> addSubjectToTeacher(int id, String code) {
        return subjectDao.addSubjectToTeacher(id, code);
    }

    public List<Subject> getNotAddedSubjectsByTeacherId(int id) {
        return subjectDao.getNotAddedSubjectsByTeacherId(id);
    }

    public boolean exists(String code) {
        return subjectDao.exists(code);
    }

    @Transactional
    public void save(Subject subject, boolean edit) {
        if(!edit) {
            subjectDao.createSubject(subject);
        }
        else {
            System.out.println(subject.getPrice());
            subjectDao.update(subject);
        }
    }

    public List<User> getTeachersBySubjectCode(String code) {
        return subjectDao.getTeachersBySubjectCode(code);
    }

    public List<User> getNotAddedTeachersBySubjectCode(String code) {
        return subjectDao.getNotAddedTeachersBySubjectCode(code);
    }

    public List<User> addTeacherToSubject(String code, int id) {
        return subjectDao.addTeacherToSubject(code, id);
    }

    public void deleteSubject(String code) {
        subjectDao.deleteSubject(code);
    }
}