package com.epam.esm.dao.impl;

import com.epam.esm.dao.query.SqlUserQuery;
import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDAOImpl {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> allUsers(String sort){
        return jdbcTemplate.query(SqlUserQuery.SELECT_ALL_USER + sort, new BeanPropertyRowMapper<>(User.class));
    }

    public User getOneUserById(int id){
        return jdbcTemplate.query(SqlUserQuery.SELECT_USER_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }

    public int addUser(String name) {
        return jdbcTemplate.update(SqlUserQuery.ADD_USER, name);
    }
}
