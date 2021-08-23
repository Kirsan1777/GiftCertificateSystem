package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.model.*;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class for realise interface UserService
 */
@Component
public class UserServiceImpl  {
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final GiftCertificateServiceImpl giftCertificateService;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, OrderDAO orderDAO, GiftCertificateServiceImpl giftCertificateService, ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.giftCertificateService = giftCertificateService;
        this.modelMapper = modelMapper;
    }

    public void addUser(User user){
        userDAO.save(user);
    }

    public Page<User> getAllUsers(Pageable pageable){
        return userDAO.findAll(pageable);
    }

    public UserDto getUserById(int idUser){
        Optional<User> user = userDAO.findById(idUser);
        UserDto userDto = user.map( u -> modelMapper.map(u, UserDto.class)).get();
        return userDto;
    }

    public List<UserOrderDto> getTheMostExpensiveOrder(int id){
        List<UserOrderDto> mostExpensiveOrder;
            mostExpensiveOrder = orderDAO.findMostExpensiveUserOrder(id).stream()
                    .map(o -> modelMapper.map(o, UserOrderDto.class)).collect(Collectors.toList());
        return mostExpensiveOrder;
    }

}
