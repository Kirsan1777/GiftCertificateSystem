package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.*;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserOrderDto;
import com.epam.esm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * The class for realise interface UserService
 */
@Component
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final TagDAO tagDAO;
    private final GiftCertificateServiceImpl giftCertificateService;
    private ModelMapper modelMapper;
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, OrderDAO orderDAO, TagDAO tagDAO, GiftCertificateServiceImpl giftCertificateService, ModelMapper modelMapper, PasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.tagDAO = tagDAO;
        this.giftCertificateService = giftCertificateService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    public String createUser(UserDto newUser) {
        Optional<UserDto> createdUser;
        if (isUsernameFree(newUser.getUsername())) {
            User user = modelMapper.map(newUser, User.class);
            user.setPassword(encoder.encode(newUser.getPassword()));
            user.setRole(UserRole.CLIENT);
            user.setStatus(Status.ACTIVE);
            createdUser = Optional.of(modelMapper.map(userDAO.save(user), UserDto.class));
            createdUser.get().setPassword(null);
            createdUser.get().setOperationStatus("User was created");
        } else {
            createdUser = Optional.of(modelMapper.map(newUser, UserDto.class));
            createdUser.get().setOperationStatus("Username is taken");
        }
        return createdUser.get().getOperationStatus();
    }

    public Page<User> getAllUsers(Pageable pageable){
        return userDAO.findAll(pageable);
    }

    public UserDto getUserById(int idUser){
        Optional<User> user = userDAO.findById(idUser);
        UserDto userDto = user.map( u -> modelMapper.map(u, UserDto.class)).get();
        return userDto;
    }

    public Page<UserOrderDto> getTheMostExpensiveOrder(int id, Pageable pageable){
        List<UserOrderDto> mostExpensiveOrder;
            mostExpensiveOrder = orderDAO.findMostExpensiveUserOrder(id, pageable).stream()
                    .map(o -> modelMapper.map(o, UserOrderDto.class)).collect(Collectors.toList());
        return new PageImpl<>(mostExpensiveOrder);
    }

    public List<Tag> findMostUsedUserTag(int idUser){
        return tagDAO.findMostUsedUserTag(idUser);
    }

    @Transactional
    public User loadUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    private boolean isUsernameFree(String username) {
        return isNull(userDAO.findByUsername(username));
    }
}
