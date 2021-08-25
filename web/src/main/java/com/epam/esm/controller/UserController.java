package com.epam.esm.controller;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.User;
import com.epam.esm.model.UserOrder;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserOrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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
    @PreAuthorize("hasAuthority('developers:read')")
    public Page<User> getAllUsers(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public UserDto getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/order/{idUser}")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Object> addOrder(@Valid @RequestBody UserOrder order, @PathVariable("idUser") int idUser) {
        order.setIdUser(idUser);
        orderService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('developers:read')")
    public Page<UserOrderDto> showOrders(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return HateoasManager.addLinksToListOrder(orderService.allOrders(pageable));
    }

    @GetMapping("/order/{idOrder}")
    @PreAuthorize("hasAuthority('developers:write')")
    public UserOrderDto findOrderById(@PathVariable("idOrder") int idOrder) {
        return HateoasManager.addLinkToOrder(orderService.findOrderById(idOrder));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Page<UserOrderDto> allUserOrders(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("id") int id) {
        return HateoasManager.addLinksToListOrder(orderService.allUserOrders(pageable, id));
    }

    @GetMapping("/order/popular/{idUser}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<UserOrderDto> mostExpensiveOrder(@PathVariable("idUser") int idUser) {
        // TODO: 23.08.2021  add link to certificate with opportunity get tags
        return userService.getTheMostExpensiveOrder(idUser);
    }

    @GetMapping("/tag/{idUser}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<TagDto> findMostUsedUserTag(@PathVariable ("idUser") int idUser){
        return userService.findMostUsedUserTag(idUser);
    }

    @DeleteMapping("/{idOrder}")
    @PreAuthorize("hasAuthority('developers:write')")
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
