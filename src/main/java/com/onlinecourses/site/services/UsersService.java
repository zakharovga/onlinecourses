package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.User;
import com.onlinecourses.site.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by user on 04.07.2015.
 */
@Component("usersService")
public class UsersService {
    private UsersDao usersDao;

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    public List<User> getAllUsers() {
        return usersDao.getAllUsers();
    }

    public void save(User user, String authority) {
        if(user.getId() == 0) {
            usersDao.createUser(user, authority);
        }
        else {
            update(user);
        }
    }

    public List<User> getAllTeachers() {
        return usersDao.getAllUsersByRole("ROLE_TEACHER");
    }


    public List<User> getTeachersBySubjectCode(String code) {
        return usersDao.getTeachersBySubjectCode(code);
    }

    public User getUserById(int id) {
        return usersDao.getUserById(id);
    }

    public void deleteUser(int id) {
        usersDao.deleteUser(id);
    }

    public boolean exists(String email) {
        return usersDao.exists(email);
    }

    public void update(User teacher) {
        usersDao.update(teacher);
    }
}