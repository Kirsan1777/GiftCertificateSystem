package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.dao.impl.UserDAOImpl;
import com.epam.esm.model.*;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The class for realise interface UserService
 */
@Component
public class UserServiceImpl implements UserService {
    private final UserDAOImpl userDAO;
    private final OrderDAOImpl orderDAO;
    private final GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO, OrderDAOImpl orderDAO, GiftCertificateServiceImpl giftCertificateService) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.giftCertificateService = giftCertificateService;
    }

    public void addUser(User user){
        userDAO.addUser(user);
    }

    public List<User> getAllUsers(int page, int size){
        return userDAO.allUsers(page, size);
    }

    public User getUserById(int idUser){
        return userDAO.getOneUserById(idUser);
    }

    public List<Tag> getTheMostExpensiveTag(int id){
        List<UserOrder> orders = (List<UserOrder>) orderDAO.getTheMostWidelyUsedTagOfAUserWithTheHighestCost(id);
        Map<Integer, Integer> tags = new HashMap<>();
        orders.stream().forEach(order -> {
            if (tags.containsKey(order.getIdCertificate())){
                tags.replace(order.getIdCertificate(), tags.get(order.getIdCertificate())+1);
            }else{
                tags.put(order.getIdCertificate(), 0);
            }
        });
        Optional<Map.Entry<Integer, Integer>> maxEntry = tags.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        int idCertificate = maxEntry.get().getKey();
        GiftCertificate giftCertificate = giftCertificateService.findGiftById(idCertificate);
        return giftCertificate.getTags();
    }

}
