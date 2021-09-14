package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.*;
import com.epam.esm.model.dto.UserOrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    private static OrderDAO orderDAO;

    @Mock
    private static GiftCertificateDAO giftDAO;

    @Mock
    private static UserDAO userDAO;

    private OrderService orderService;

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private final UserOrder order = new UserOrder(1, 1, 1, 1, LocalDateTime.now());
    //private final UserOrder orderSecond = new UserOrder(2, 2, 2, 2, LocalDateTime.now());
    private final UserOrderDto orderDto = new UserOrderDto(1, 1, 1, LocalDateTime.now());
    private static GiftCertificate giftCertificate = new GiftCertificate(1, "newTag", 200, 1,
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            "newGift", null);
    private final User user = new User(1, 100, "adi", "adi", Status.ACTIVE, UserRole.ADMIN);

    @BeforeEach
    void init(){
        orderService = new OrderServiceImpl(orderDAO, modelMapper(), giftDAO, userDAO);
    }

    @Test
    public void addOrderWithReturnedValueTest(){
        Optional<GiftCertificate> gift = Optional.of(giftCertificate);
        when(orderDAO.save(any(UserOrder.class))).thenReturn(order);
        when(giftDAO.findById(any(int.class))).thenReturn(gift);
        when(userDAO.findUserById(any(int.class))).thenReturn(user);
        assertEquals(order, orderService.addOrderWithResult(order));
    }

    @Test
    public void findOrderById(){
        Optional<UserOrder> userOrder = Optional.of(order);
        when(orderDAO.findById(any(int.class))).thenReturn(userOrder);
        assertEquals(orderDto, orderService.findOrderById(1));
    }

    @Test
    void addOrderTest(){
        Optional<GiftCertificate> gift = Optional.of(giftCertificate);
        when(orderDAO.save(any(UserOrder.class))).thenReturn(order);
        when(giftDAO.findById(any(int.class))).thenReturn(gift);
        when(userDAO.findUserById(any(int.class))).thenReturn(user);
        orderService.addOrder(order);
        verify(orderDAO, times(1)).save(any(UserOrder.class));
    }

    @Test
    void deleteOrderTest(){
        orderService.deleteOrder(1);
        verify(orderDAO, times(1)).deleteById(any(int.class));
    }
}
