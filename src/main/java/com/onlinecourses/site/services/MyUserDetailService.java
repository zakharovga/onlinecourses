package com.onlinecourses.site.services;

import com.onlinecourses.site.dao.MyUserDetails;
import com.onlinecourses.site.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by zakharov_ga on 15.07.2015.
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao usersDao;

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final MyUserDetails user = usersDao.loadUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("No user found for username '" + username +"'.");
        }
        return user;
    }
}