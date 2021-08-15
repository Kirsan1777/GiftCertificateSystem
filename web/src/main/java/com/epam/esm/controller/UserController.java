package com.epam.esm.controller;

import com.epam.esm.error.ErrorCode;
import com.epam.esm.error.ErrorHandler;
import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.UserOrder;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add-user")
    public ResponseEntity<Object> addUser(@RequestBody User user){
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-order")//how to bought order?
    public Iterable<UserOrder> addOrder(@RequestBody UserOrder order) {
        orderService.addOrder(order);
        return orderService.allOrders();
    }

    @GetMapping("/all-orders")
    public Iterable<UserOrder> showOrders() {
        return orderService.allOrders();
    }

    @GetMapping("/order-by-id/{idOrder}")
    public UserOrder findOrderById(@PathVariable("idOrder") int idOrder){
        return orderService.findOrderById(idOrder);
    }

    @GetMapping("/all-user-orders/{id}")
    public Iterable<UserOrder> allUserOrders(@PathVariable("id") int id) {
        return orderService.allUserOrders(id);
    }

    @GetMapping("/most-used-tag/{id}")
    public List<Tag> mostExpensiveTag(@PathVariable("id") int idUser) {
        return userService.getTheMostExpensiveTag(idUser);
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("idOrder") int idOrder){
        orderService.deleteOrder(idOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Mistake in user controller \nexception : " + ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
