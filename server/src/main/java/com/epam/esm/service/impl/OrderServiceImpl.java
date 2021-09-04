package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.User;
import com.epam.esm.model.UserOrder;
import com.epam.esm.model.dto.UserOrderDto;
import com.epam.esm.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * The class for realise interface OrderService
 */
@Component
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private ModelMapper modelMapper;
    private GiftCertificateDAO certificateDAO;
    private UserDAO userDAO;

    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ModelMapper modelMapper, GiftCertificateDAO certificateDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.modelMapper = modelMapper;
        this.certificateDAO = certificateDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Page<UserOrderDto> allOrders(Pageable pageable) {
        return orderDAO.findAll(pageable)
                .map(c -> modelMapper.map(c, UserOrderDto.class));
    }

    @Override
    public void deleteOrder(int idOrder) {
        orderDAO.deleteById(idOrder);
    }

    @Override
    public void addOrder(UserOrder order) {
        order.setTimeOfPurchase(LocalDateTime.now());
        order.setCost(certificateDAO.findById(order.getIdCertificate()).get().getPrice());
        User user = userDAO.findUserById(order.getIdUser());
        if (isNull(user)) {
            throw new BadCredentialsException("user not found");
        }
        orderDAO.save(order);
    }

    @Override
    public UserOrderDto findOrderById(int id) {
        Optional<UserOrder> userOrder = orderDAO.findById(id);
        UserOrderDto userOrderDto;
        userOrderDto = userOrder.map(order -> modelMapper.map(order, UserOrderDto.class)).get();
        return userOrderDto;
    }

    @Override
    public Page<UserOrderDto> allUserOrders(Pageable pageable, int idUser){
        Page<UserOrder> orders = orderDAO.findUserOrderByIdUser(pageable,idUser);
        Page<UserOrderDto> ordersDto = orders.map(order -> modelMapper.map(order, UserOrderDto.class));
        return ordersDto;
    }


}
