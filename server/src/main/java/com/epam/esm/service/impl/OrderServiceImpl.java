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
    private GiftCertificateServiceImpl giftCertificateService;

    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderDAOImpl orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setGiftCertificateServiceImpl(GiftCertificateServiceImpl giftCertificateService){
        this.giftCertificateService = giftCertificateService;
    }

    public Iterable<UserOrder> allOrders(int page, int size) {
        return orderDAO.allOrders(page, size);
    }

    public void deleteOrder(int idOrder) {
        orderDAO.deleteOrder(idOrder);
    }

    public void addOrder(UserOrder order) {
        order.setTimeOfPurchase(LocalDateTime.now());
        order.setCost(giftCertificateService.findGiftById(order.getIdCertificate()).getPrice());
        orderDAO.addOrder(order);
    }

    public UserOrder findOrderById(int id) {
        return orderDAO.getOneOrderById(id);
    }

    public Iterable<UserOrder> allUserOrders(int idUser){
        return orderDAO.getAllUserOrders(idUser);
    }


}
