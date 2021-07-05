package com.epam.esm.dao.query;

public class SqlTagQuery {
    public static final String ADD_TAG = "INSERT tag(name) VALUES (?)";
    public static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";
    public static final String SELECT_ALL_TAG = "SELECT * FROM tag";
    public static final String SELECT_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
    public static final String SELECT_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
}
