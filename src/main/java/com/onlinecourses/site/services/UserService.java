package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.User;

import java.util.List;

/**
 * Created by zakharov_ga on 01.10.2015.
 */
public interface UserService {
    List<User> getAllUsers();

    void save(User user, String authority);

    List<User> getAllTeachers();

    List<User> getTeachersBySubjectCode(String code);

    User getUserById(int id);

    void deleteUser(int id);

    boolean exists(String email);

    void update(User teacher);
}