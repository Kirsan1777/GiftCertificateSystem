package com.epam.esm.controller;

import com.epam.esm.error.ErrorCode;
import com.epam.esm.error.ErrorHandler;
import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.Tag;
import com.epam.esm.model.UserOrder;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController extends HateoasManager<UserOrder> {

    private OrderServiceImpl orderService;
    private UserServiceImpl userService;

    @Autowired
    public UserController(OrderServiceImpl orderService, UserServiceImpl userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /*@GetMapping()
    public List<GiftCertificate> getAllUsers(){
        //return giftCertificate.allGiftCertificate(ASC);
    }

    @GetMapping("/{id}")
    public GiftCertificate getUserById(@PathVariable("id") int id) {
        //return giftCertificate.findGiftById(id);
    }*/

    @PostMapping("/add-order")
    public Iterable<UserOrder> addOrder(@RequestBody UserOrder order) {
        orderService.addOrder(order);
        return orderService.allOrders();
    }

    @GetMapping("/all-orders")
    public Iterable<UserOrder> showOrders() {
        return orderService.allOrders();
    }

    @GetMapping("/all-user-orders/{id}")
    public Iterable<UserOrder> allUserOrders(@PathVariable("id") int id) {
        return orderService.allUserOrders(id);
    }

    @GetMapping("/most-used-tag/{id}")
    public List<Tag> mostExpensiveTag(@PathVariable("id") int idUser) {
        return userService.getTheMostExpensiveTag(idUser);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorHandler handleResourceNotFoundException(Exception exception) {
        return new ErrorHandler(exception.getMessage(), ErrorCode.RESOURCE_NOT_FOUND.getErrorCode());
    }
}
