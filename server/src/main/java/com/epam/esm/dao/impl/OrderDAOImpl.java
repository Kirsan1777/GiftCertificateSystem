package com.epam.esm.dao.impl;

import com.epam.esm.dao.query.SqlOrderQuery;
import com.epam.esm.model.userOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDAOImpl {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<userOrder> allOrders(String sort){
        return jdbcTemplate.query(SqlOrderQuery.SELECT_ALL_USERS_ORDERS + sort, new BeanPropertyRowMapper<>(userOrder.class));
    }

    public userOrder getOneOrderById(int id){
        return jdbcTemplate.query(SqlOrderQuery.SELECT_ORDER_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(userOrder.class)).stream().findAny().orElse(null);
    }

    public userOrder getMaxUserOrder(int id){
        return jdbcTemplate.query(SqlOrderQuery.SELECT_ORDER_BY_MAX_COST, new Object[]{id},
                new BeanPropertyRowMapper<>(userOrder.class)).stream().findAny().orElse(null);
    }

    public int addOrder(userOrder order) {
        return jdbcTemplate.update(SqlOrderQuery.ADD_ORDER, order.getIdUser(), order.getIdCertificate(),
                order.getCost(), order.getTimeOfPurchase());
    }

}

