package com.epam.esm.dao.impl;

import com.epam.esm.dao.query.SqlOrderQuery;
import com.epam.esm.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAOImpl {

    private final EntityManager entityManager;

    private static final String GET_ALL_ORDERS = "SELECT user_order FROM UserOrder user_order";

    @Autowired
    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<UserOrder> allOrders(){
        return entityManager.createQuery(GET_ALL_ORDERS, UserOrder.class).getResultList();
    }

    @Transactional
    public UserOrder getOneOrderById(int id){
        return entityManager.find(UserOrder.class, id);
    }

    @Transactional
    public Optional<UserOrder> getMaxUserOrder(int id){
        return allOrders().stream().max(Comparator.comparing(UserOrder::getCost));
    }

    @Transactional
    public void addOrder(UserOrder order) {
        entityManager.persist(order);
    }

}

