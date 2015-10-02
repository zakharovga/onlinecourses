package com.onlinecourses.site.dao;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by zakharov_ga on 01.10.2015.
 */
public interface UserDao {
    List<User> getAllUsers();

    void createUser(User user, String authority);

    List<User> getAllUsersByRole(String authority);

    List<User> getTeachersBySubjectCode(String code);

    User getUserById(int id);

    boolean update(User user);

    boolean deleteUser(int id);

    boolean exists(String email);

    User getUserByEmail(String email);

    MyUserDetails loadUserByUsername(String username);

    List<MyUserDetails> loadUsersByUsername(String email);

    List<GrantedAuthority> loadUserAuthorities(String email);

    MyUserDetails createUserDetails(String username, MyUserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities);
}