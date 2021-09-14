package com.epam.esm.service;

import com.epam.esm.model.UserOrder;
import com.epam.esm.model.dto.UserOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The interface order service.
 */
public interface OrderService {

    /**
     * Method for getting all orders
     *
     * @param pageable the setting for class pageable
     */
    Page<UserOrderDto> allOrders(Pageable pageable);

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
     * Method for add one order
     *
     * @param order the order entity
     */
    UserOrder addOrderWithResult(UserOrder order);

    /**
     * Method for getting one order by id
     *
     * @param id the order id
     */
    UserOrderDto findOrderById(int id);

    /**
     * Method for getting all user orders
     *
     * @param idUser the order id
     * @param pageable the setting for class pageable
     */
    Page<UserOrderDto> allUserOrders(Pageable pageable, int idUser);
}
