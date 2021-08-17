package com.epam.esm.dao;

import com.epam.esm.model.UserOrder;

import java.util.List;

/**
 * The interface order DAO.
 */
public interface OrderDAO {

    /**
     * Method for getting all orders
     *
     * @param page the number of page
     * @param size the number object for view
     */
    List<UserOrder> allOrders(int page, int size);

    /**
     * Method for getting one order by id
     *
     * @param id the order id
     */
    UserOrder getOneOrderById(int id);

    /**
     * Method for delete one order by id
     *
     * @param id the order id
     */
    void deleteOrder(int id);

    /**
     * Method for add one order
     *
     * @param order the order entity
     */
    void addOrder(UserOrder order);

    /**
     * Method for getting all user orders
     *
     * @param id the order id
     */
    Iterable<UserOrder> getAllUserOrders(int id);

    /**
     * Method for getting most widely used tag of a user with the highest cost
     *
     * @param id the user id
     */
    Iterable<UserOrder> getTheMostWidelyUsedTagOfAUserWithTheHighestCost(int id);
}
