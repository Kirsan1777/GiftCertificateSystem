package com.epam.esm;

import com.epam.esm.model.UserOrder;
import com.epam.esm.model.dto.UserOrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @Mock
    private static OrderServiceImpl orderService;

    private final UserOrder order = new UserOrder(1, 1, 1, 1, LocalDateTime.now());
    //private final UserOrder orderSecond = new UserOrder(2, 2, 2, 2, LocalDateTime.now());
    private final UserOrderDto orderDto = new UserOrderDto(1, 1, 1, LocalDateTime.now());


    @Test
    public void addOrderTest(){
        when(orderService.addOrderWithResult(any(UserOrder.class))).thenReturn(order);
        assertEquals(order, orderService.addOrderWithResult(order));
    }

    @Test
    public void findOrderById(){
        when(orderService.findOrderById(any(int.class))).thenReturn(orderDto);
        assertEquals(orderDto, orderService.findOrderById(1));
    }

}
