package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.dao.impl.UserDAOImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.Tag;
import com.epam.esm.model.UserOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserServiceImpl {
    private final UserDAOImpl userDAO;
    private final OrderDAOImpl orderDAO;
    private final GiftCertificateServiceImpl giftCertificateService;
    private final GiftTagServiceImpl giftTagService;

    /*public UserServiceImpl() {
        mb this constructor will be use later
    }*/
    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO, OrderDAOImpl orderDAO, GiftCertificateServiceImpl giftCertificateService, GiftTagServiceImpl giftTagService) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.giftCertificateService = giftCertificateService;
        this.giftTagService = giftTagService;
    }



    public List<GiftTag> getTheMostExpensiveTag(int id){
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
        return giftTagService.getConcatenatedTablesByIdGiftCertificate(giftCertificate.getId());
    }

}
