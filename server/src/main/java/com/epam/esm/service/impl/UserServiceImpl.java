package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.*;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class UserServiceImpl  {
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

    public void addUser(User user){
        userDAO.save(user);
    }

    public Optional<UserDto> createUser(UserDto newUser) {
        Optional<UserDto> createdUser;
        if (isUsernameFree(newUser.getUsername())) {
            User user = modelMapper.map(newUser, User.class);
            user.setPassword(encoder.encode(newUser.getPassword()));
            user.setRole(UserRole.CLIENT);
            user.setStatus(Status.ACTIVE);
            //user.setCertificateOrders(new HashSet<>());
            createdUser = Optional.of(modelMapper.map(userDAO.save(user), UserDto.class));
            createdUser.get().setOperationStatus("User was created");
        } else {
            createdUser = Optional.of(modelMapper.map(newUser, UserDto.class));
            createdUser.get().setOperationStatus("Username is taken");
        }
        return createdUser;
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

    public List<TagDto> findMostUsedUserTag(int idUser){
        List<TagDto> mostUsedUserTag;
        mostUsedUserTag = tagDAO.findMostUsedUserTag(idUser).stream()
                .map(o -> modelMapper.map(o, TagDto.class)).collect(Collectors.toList());
         tagDAO.findMostUsedUserTag(idUser);
        return mostUsedUserTag;
    }

    private boolean isUsernameFree(String username) {
        return isNull(userDAO.findByUsername(username));
    }

    @Transactional
    public User loadUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

}
