package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * The class for realise interface GiftCertificateService
 */
@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDAOImpl giftCertificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAOImpl giftCertificateDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
    }

    public void addGiftCertificate(GiftCertificate gift){
        gift.setCreateDate(LocalDateTime.now());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
    }

    public void deleteGiftCertificate(int id){
        giftCertificateDAO.deleteById(id);
    }

    public Collection<GiftCertificate> allGiftCertificate(int page, int size){
        return giftCertificateDAO.allCertificate(page, size);
    }

    public void updateGiftCertificate(GiftCertificate gift){
        gift.setCreateDate(giftCertificateDAO.readOneGiftById(gift.getId()).getCreateDate());
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
