package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The class for realise interface GiftCertificateService
 */
@Component
public class GiftCertificateServiceImpl {

    @Autowired
    private GiftCertificateDAOImpl giftCertificateDAO;
    private GiftCertificateDAO giftCertificate;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAOImpl giftCertificateDAO, GiftCertificateDAO giftCertificate) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificate = giftCertificate;
    }

    public void addGiftCertificate(GiftCertificate gift){
        gift.setCreateDate(LocalDateTime.now());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
    }

    public void deleteGiftCertificate(int id){
        giftCertificateDAO.deleteById(id);
    }

    public Iterable<GiftCertificate> allGiftCertificate(){
        return giftCertificateDAO.allCertificate();
    }

    public void updateGiftCertificate(GiftCertificate gift){
        gift.setLastUpdateDate(LocalDateTime.now());
        //GiftCertificate giftTest = new GiftCertificate
        //        (1, "1", 1, 1, LocalDateTime.now(), LocalDateTime.now(), "TestHibernate");
        giftCertificateDAO.updateCertificate(gift);
    }

    public void updateGiftCertificatePrice(int idGift, double price){
        giftCertificateDAO.updateCertificatePrice(idGift, price);
    }

    public GiftCertificate findGiftById(int id){
        return giftCertificateDAO.readOneGiftById(id);
    }
}
