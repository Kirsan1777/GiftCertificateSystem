package com.epam.esm.dao.impl;

import com.epam.esm.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String GET_ALL_USER_ORDERS = "SELECT user_order FROM UserOrder user_order WHERE user_order.idUser = :id";
    private static final String GET_MOST_POPULAR_USER_TAG = "SELECT user_order FROM UserOrder user_order WHERE user_order.cost = " +
            "(SELECT MAX(user_order.cost) FROM UserOrder user_order) AND user_order.idUser = :id";

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
    public void deleteOrder(int id){
        entityManager.remove(getOneOrderById(id));
    }

    @Transactional
    public Optional<UserOrder> getMaxUserOrder(int id){
        return allOrders().stream().max(Comparator.comparing(UserOrder::getCost));
    }

    @Transactional
    public void addOrder(UserOrder order) {
        entityManager.persist(order);
    }

    @Transactional
    public Iterable<UserOrder> getAllUserOrders(int id){
        return entityManager.createQuery(GET_ALL_USER_ORDERS, UserOrder.class).setParameter("id", id).getResultList();
    }

    @Transactional
    public Iterable<UserOrder> getTheMostWidelyUsedTagOfAUserWithTheHighestCost(int id){
        //this method return value: order with the most cost, not tag
        return entityManager.createQuery(GET_MOST_POPULAR_USER_TAG, UserOrder.class).setParameter("id", id).getResultList();
    }

}

