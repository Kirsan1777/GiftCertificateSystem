package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.UserOrder;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.UserOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The class for realise interface OrderService
 */
@Component
public class OrderServiceImpl {
    private OrderDAO orderDAO;
    private ModelMapper modelMapper;
    private GiftCertificateDAO certificateDAO;

    public OrderServiceImpl() {
    }

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ModelMapper modelMapper, GiftCertificateDAO certificateDAO) {
        this.orderDAO = orderDAO;
        this.modelMapper = modelMapper;
        this.certificateDAO = certificateDAO;
    }


    public Page<UserOrderDto> allOrders(Pageable pageable) {
        return orderDAO.findAll(pageable)
                .map(c -> modelMapper.map(c, UserOrderDto.class));
    }

    public void deleteOrder(int idOrder) {
        orderDAO.deleteById(idOrder);
    }

    public void addOrder(UserOrder order) {
        order.setTimeOfPurchase(LocalDateTime.now());
        //Optional<GiftCertificate> certificate = certificateDAO.findById(order.getIdCertificate());
        order.setCost(certificateDAO.findById(order.getIdCertificate()).get().getPrice());
        //check isPresent?
        orderDAO.save(order);
    }

    public UserOrderDto findOrderById(int id) {
        Optional<UserOrder> userOrder = orderDAO.findById(id);
        UserOrderDto userOrderDto;
        userOrderDto = userOrder.map(order -> modelMapper.map(order, UserOrderDto.class)).get();
        return userOrderDto;
    }

    public Page<UserOrderDto> allUserOrders(Pageable pageable, int idUser){
        Page<UserOrder> orders = orderDAO.findUserOrderByIdUser(pageable,idUser);
        Page<UserOrderDto> ordersDto = orders.map(order -> modelMapper.map(order, UserOrderDto.class));
        return ordersDto;
    }


}
