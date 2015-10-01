package com.onlinecourses.site.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by user on 04.07.2015.
 */
@Component("usersDao")
public class UserDaoImpl implements UserDao {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource jdbc) {
//        Locale.setDefault(Locale.ENGLISH);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc);
        this.jdbcTemplate = new JdbcTemplate(jdbc);
    }

    public List<User> getAllUsers() {
        return namedParameterJdbcTemplate.query("select * from users", new UsersRowMapper());
    }

    public void createUser(User user, String authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        int nextUserId = jdbcTemplate.queryForObject("select users_sequence.NEXTVAL from dual", Integer.class);

        params.addValue("email", user.getEmail());
        params.addValue("lastName", user.getLastName());
        params.addValue("firstName", user.getFirstName());
        params.addValue("phoneNumber", user.getPhoneNumber());
        params.addValue("country", user.getCountry());
        params.addValue("city", user.getCity());
        params.addValue("birthDate", user.getBirthDate());
        params.addValue("enabled", user.isEnabled());
        params.addValue("description", user.getDescription());
        params.addValue("password", user.getPassword());
        params.addValue("id", nextUserId);
        params.addValue("authority", authority);

        namedParameterJdbcTemplate.update("insert into users (user_id, email, last_name, first_name," +
                "phone_number, country, city, birth_date, registration_date, enabled, description, password) values (:id, :email," +
                ":lastName, :firstName, :phoneNumber, :country, :city, to_date(:birthDate, 'MM/DD/YYYY'), (select sysdate from dual), :enabled, :description, :password)", params);

        namedParameterJdbcTemplate.update("insert into authorities (user_id, authority) values (:id, :authority)", params);
    }

    public List<User> getAllUsersByRole(String authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authority", authority);
        return namedParameterJdbcTemplate.query("select users.user_id, users.email, users.last_name, users.first_name, users.phone_number, users.country, users.city, users.birth_date, users.registration_date, users.enabled, users.description from users, authorities where users.user_id = authorities.user_id and authorities.authority = :authority", params, new UsersRowMapper());
    }

    public List<User> getTeachersBySubjectCode(String code) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", code);
        return namedParameterJdbcTemplate.query("select * from users, subject_teacher where users.user_id = subject_teacher.user_id and subject_teacher.subject_code = :code", params, new UsersRowMapper());
    }

    public User getUserById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject("select * from users where user_id = :id", params, new UsersRowMapper());
    }

    public boolean update(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("lastName", user.getLastName());
        params.addValue("firstName", user.getFirstName());
        params.addValue("phoneNumber", user.getPhoneNumber());
        params.addValue("country", user.getCountry());
        params.addValue("city", user.getCity());
        params.addValue("description", user.getDescription());

        return namedParameterJdbcTemplate.update("update users set last_name = :lastName, first_name = :firstName, phone_number = :phoneNumber, country = :country, city = :city, description = :description where user_id = :id", params) == 1;
    }

    public boolean deleteUser(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.update("delete from users where user_id=:id", params) == 1;
    }

    public boolean exists(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        return namedParameterJdbcTemplate.queryForObject("select count(*) from users where email = :email", params, Integer.class) > 0;
    }

    public User getUserByEmail(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        return namedParameterJdbcTemplate.queryForObject("select * from users where email = :email", params, new UsersRowMapper());
    }

    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = this.loadUsersByUsername(username);
        if(users.size() == 0) {
            throw new UsernameNotFoundException("Username not found");
        } else {
            MyUserDetails user = (MyUserDetails)users.get(0);
            HashSet dbAuthsSet = new HashSet();
            dbAuthsSet.addAll(this.loadUserAuthorities(user.getUsername()));

            ArrayList dbAuths = new ArrayList(dbAuthsSet);

            if(dbAuths.size() == 0) {
                throw new UsernameNotFoundException("User has no GrantedAuthority");
            } else {
                return this.createUserDetails(username, user, dbAuths);
            }
        }
    }

    public List<MyUserDetails> loadUsersByUsername(String email) {
        return jdbcTemplate.query("SELECT email, password, enabled, user_id, last_name, first_name FROM users WHERE email = ?", new String[]{email}, new RowMapper() {
            public MyUserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                int id = rs.getInt(4);
                String lastName = rs.getString(5);
                String firstName = rs.getString(6);
                return new MyUserDetails(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, id, lastName, firstName);
            }
        });
    }

    public List<GrantedAuthority> loadUserAuthorities(String email) {
        return jdbcTemplate.query("SELECT users.email, authorities.authority FROM users, authorities WHERE authorities.user_id = users.user_id and users.email = ?", new String[]{email}, new RowMapper() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = rs.getString(2);
                return new SimpleGrantedAuthority(roleName);
            }
        });
    }

    public MyUserDetails createUserDetails(String username, MyUserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        return new MyUserDetails(username, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities, userFromUserQuery.getId(), userFromUserQuery.getLastName(), userFromUserQuery.getFirstName());
    }
}