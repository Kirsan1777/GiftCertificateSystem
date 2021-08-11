package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.Tag;
import com.epam.esm.model.UserOrder;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderServiceImpl {
    private OrderDAOImpl orderDAO;

    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderDAOImpl orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Iterable<UserOrder> allOrders() {
        return orderDAO.allOrders();
    }

    public void deleteOrder(int idOrder) {
        orderDAO.deleteOrder(idOrder);
    }

    public void addOrder(UserOrder order) {
        order.setTimeOfPurchase(LocalDateTime.now());
        orderDAO.addOrder(order);
    }

    public UserOrder findById(int id) {
        return orderDAO.getOneOrderById(id);
    }

    public Iterable<UserOrder> allUserOrders(int idUser){
        return orderDAO.getAllUserOrders(idUser);
    }


}
