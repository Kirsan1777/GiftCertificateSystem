package com.epam.esm.controller;

import com.epam.esm.model.UserOrder;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController {

    private OrderServiceImpl orderService;

    @Autowired
    public UserController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

       /* @GetMapping()
    public List<GiftCertificate> getAllUsers(){
        //return giftCertificate.allGiftCertificate(ASC);
    }

    @GetMapping("/{id}")
    public GiftCertificate getUserById(@PathVariable("id") int id) {
        //return giftCertificate.findGiftById(id);
    }*/

    @PostMapping("/addOrder")
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
