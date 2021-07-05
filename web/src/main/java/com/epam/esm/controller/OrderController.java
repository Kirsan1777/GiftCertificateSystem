package com.epam.esm.controller;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.model.userOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@EnableAutoConfiguration
public class OrderController {

    @Autowired
    private OrderDAO orderDAO;

    public OrderController() {
    }

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public Iterable<userOrder> addOrder(){
        /*Order order = new Order(1, 1, 1, 100, LocalDateTime.now());
        orderDAO.save(order);
        return "Rabotaet?";*/
        return orderDAO.findAll();
    }
}
