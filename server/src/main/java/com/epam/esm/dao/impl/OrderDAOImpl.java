package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Class for realise interface OrderDAO
 */
@Repository
public class OrderDAOImpl {

    private final EntityManager entityManager;

    private static final String GET_ALL_USER_ORDERS = "SELECT user_order FROM UserOrder user_order WHERE user_order.idUser = :id";
    private static final String GET_MOST_POPULAR_USER_TAG = "SELECT user_order FROM UserOrder user_order WHERE user_order.cost = " +
            "(SELECT MAX(user_order.cost) FROM UserOrder user_order) AND user_order.idUser = :id";

    @Autowired
    public OrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<UserOrder> allOrders(int page, int size) {
        CriteriaQuery<UserOrder> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(UserOrder.class);
        Root<UserOrder> root = criteriaQuery.from(UserOrder.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public UserOrder getOneOrderById(int id) {
        return entityManager.find(UserOrder.class, id);
    }

    @Transactional
    public void deleteOrder(int id) {
        entityManager.remove(getOneOrderById(id));
    }

    @Transactional
    public void addOrder(UserOrder order) {
        entityManager.persist(order);
    }

    @Transactional
    public Iterable<UserOrder> getAllUserOrders(int id) {
        return entityManager.createQuery(GET_ALL_USER_ORDERS, UserOrder.class).setParameter("id", id).getResultList();
    }

    @Transactional
    public Iterable<UserOrder> getTheMostWidelyUsedTagOfAUserWithTheHighestCost(int id) {
        return entityManager.createQuery(GET_MOST_POPULAR_USER_TAG, UserOrder.class).setParameter("id", id).getResultList();
    }

}

