package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.User;
import com.onlinecourses.site.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by user on 04.07.2015.
 */
@Service
public class DefaultUserService implements UserService {

    private static final SecureRandom RANDOM;
    private static final int HASHING_ROUNDS = 10;

    static {
        try {
            RANDOM = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public void save(User user, String authority) {
        if(user.getId() == 0) {
            String password = user.getPassword();
            if (password != null && password.length() > 0) {
                String salt = BCrypt.gensalt(HASHING_ROUNDS, RANDOM);
                user.setPassword(BCrypt.hashpw(password, salt));
            }
            userDao.createUser(user, authority);
        }
        else {
            update(user);
        }
    }

    public List<User> getAllTeachers() {
        return userDao.getAllUsersByRole("ROLE_TEACHER");
    }


    public List<User> getTeachersBySubjectCode(String code) {
        return userDao.getTeachersBySubjectCode(code);
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public boolean exists(String email) {
        return userDao.exists(email);
    }

    public void update(User teacher) {
        userDao.update(teacher);
    }
}