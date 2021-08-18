package com.epam.esm.controller;

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

/**
 * Class of user controller to handle requests and response
 */
@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController extends HateoasManager<User> {

    private OrderServiceImpl orderService;
    private UserServiceImpl userService;

    @Autowired
    public UserController(OrderServiceImpl orderService, UserServiceImpl userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public List<User> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add-user")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-order/{idUser}")
    public ResponseEntity<Object> addOrder(@RequestBody UserOrder order, @PathVariable("idUser") int idUser) {
        order.setIdUser(idUser);
        orderService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all-orders")
    public Iterable<UserOrder> showOrders(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                          @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return HateoasManager.addLinksToListOrder(orderService.allOrders(page, size));
    }

    @GetMapping("/order-by-id/{idOrder}")
    public UserOrder findOrderById(@PathVariable("idOrder") int idOrder) {
        return HateoasManager.addLinkToOrder(orderService.findOrderById(idOrder));
    }

    @GetMapping("/all-user-orders/{id}")
    public Iterable<UserOrder> allUserOrders(@PathVariable("id") int id) {
        return HateoasManager.addLinksToListOrder(orderService.allUserOrders(id));
    }

    @GetMapping("/most-used-tag/{id}")
    public Iterable<Tag> mostExpensiveTag(@PathVariable("id") int idUser) {
        return HateoasManager.addLinksToTags(userService.getTheMostExpensiveTag(idUser));
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("idOrder") int idOrder) {
        orderService.deleteOrder(idOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                "Exception in user controller \nexception : " + ex.getMessage() + "\n" + HttpStatus.FORBIDDEN, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
