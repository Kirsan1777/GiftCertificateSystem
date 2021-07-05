package com.epam.esm.dao.query;

public class SqlUserQuery {
    public static final String ADD_USER = "INSERT users(name) VALUES (?)";
    public static final String SELECT_ALL_USER = "SELECT * FROM users";
    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
}
