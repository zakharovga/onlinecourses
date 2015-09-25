package com.onlinecourses.site.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by user on 04.07.2015.
 */
public class UsersRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setLastName(resultSet.getString("last_name"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setCountry(resultSet.getString("country"));
        user.setCity(resultSet.getString("city"));
        user.setBirthDate(resultSet.getString("birth_date"));
        user.setRegistrationDate(resultSet.getString("registration_date"));
        user.setEnabled(resultSet.getBoolean("enabled"));
        user.setDescription(resultSet.getString("description"));

        return user;
    }
}