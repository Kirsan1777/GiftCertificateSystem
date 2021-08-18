package com.epam.esm.service;

import com.epam.esm.model.UserOrder;

/**
 * The interface order service.
 */
public interface OrderService {

    /**
     * Method for getting all orders
     *
     * @param page the number of page
     * @param size the number object for view
     */
    Iterable<UserOrder> allOrders(int page, int size);

    /**
     * Method for delete one order by id
     *
     * @param idOrder the order id
     */
    void deleteOrder(int idOrder);

    /**
     * Method for add one order
     *
     * @param order the order entity
     */
    void addOrder(UserOrder order);

    /**
     * Method for getting one order by id
     *
     * @param id the order id
     */
    UserOrder findOrderById(int id);

    /**
     * Method for getting all user orders
     *
     * @param idUser the order id
     */
    Iterable<UserOrder> allUserOrders(int idUser);
}
