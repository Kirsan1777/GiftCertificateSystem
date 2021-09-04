package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.UserOrder;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserOrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    @PreAuthorize("hasAuthority('write')")
    public Page<User> getAllUsers(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public UserDto getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/order/{idUser}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody UserOrder order, @PathVariable("idUser") int idUser) {
        order.setIdUser(idUser);
        orderService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('read')")
    public Page<UserOrderDto> showOrders(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return HateoasManager.addLinksToListOrder(orderService.allOrders(pageable));
    }

    @GetMapping("/order/{idOrder}")
    @PreAuthorize("hasAuthority('write')")
    public UserOrderDto findOrderById(@PathVariable("idOrder") int idOrder) {
        return HateoasManager.addLinkToOrder(orderService.findOrderById(idOrder));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAuthority('read')")
    public Page<UserOrderDto> allUserOrders(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("id") int id) {
        return HateoasManager.addLinksToListOrder(orderService.allUserOrders(pageable, id));
    }

    @GetMapping("/order/popular/{idUser}")
    @PreAuthorize("hasAuthority('read')")
    public Page<UserOrderDto> mostExpensiveOrder(@PathVariable("idUser") int idUser, @PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // TODO: 23.08.2021  add links to certificate with opportunity get tags
        return HateoasManager.addLinksToListOrder(userService.getTheMostExpensiveOrder(idUser, pageable));
    }

    @GetMapping("/tag/{idUser}")
    @PreAuthorize("hasAuthority('read')")
    public List<Tag> findMostUsedUserTag(@PathVariable ("idUser") int idUser){
        return HateoasManager.addLinksToListTags(userService.findMostUsedUserTag(idUser));
    }

    @DeleteMapping("/{idOrder}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> deleteOrder(@PathVariable("idOrder") int idOrder) {
        orderService.deleteOrder(idOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
