package com.epam.esm.controller;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.model.Tag;
import com.epam.esm.model.UserOrder;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@EnableAutoConfiguration
public class OrderController {

    private OrderServiceImpl orderService;

    public OrderController() {
    }

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public Iterable<UserOrder> addOrder(@ModelAttribute("order") UserOrder order){
        orderService.addOrder(order);
        return orderService.allOrders();
    }

    @GetMapping("/allOrders")
    public Iterable<UserOrder> showOrder(){
        return orderService.allOrders();
    }

    @GetMapping("/allUserOrders/{id}")
    public Iterable<UserOrder> allUserOrders(@PathVariable("id") int id){
        return orderService.allUserOrders(id);
    }
}
