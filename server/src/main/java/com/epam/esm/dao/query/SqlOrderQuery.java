package com.epam.esm.dao.query;

public class SqlOrderQuery {
    public static final String ADD_ORDER = "INSERT orders(idUser, idCertificate, cost, time_of_purchase) VALUES (?, ?, ?, ?)";
    public static final String SELECT_ALL_USERS_ORDERS = "SELECT * FROM orders";
    public static final String SELECT_ORDER_BY_ID = "SELECT * FROM orders WHERE idUser = ?";
    public static final String SELECT_ORDER_BY_MAX_COST = "SELECT * FROM orders WHERE cost = (SELECT MAX(cost) FROM orders) AND idUser = ?)";
}
