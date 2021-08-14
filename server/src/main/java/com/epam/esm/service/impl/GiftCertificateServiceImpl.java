package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
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

    public Collection<GiftCertificate> allGiftCertificate(){
        return giftCertificateDAO.allCertificate();
    }

    public void updateGiftCertificate(GiftCertificate gift){
        gift.setCreateDate(giftCertificateDAO.readOneGiftById(gift.getId()).getCreateDate());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.updateCertificate(gift);
    }

    public void updateGiftCertificatePrice(int idGift, double price){
        giftCertificateDAO.updateCertificatePrice(idGift, price);
    }

    public GiftCertificate findGiftById(int id){
        return giftCertificateDAO.readOneGiftById(id);
    }

    public List<GiftCertificate> allGiftCertificateByTags(List<String> tagsName){
        return giftCertificateDAO.allGiftCertificateByTags(tagsName);
    }
}
