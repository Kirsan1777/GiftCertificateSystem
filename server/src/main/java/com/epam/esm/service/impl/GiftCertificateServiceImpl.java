package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.*;

/**
 * The class for realise interface GiftCertificateService
 */
@Component
public class GiftCertificateServiceImpl {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;
    private ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void addGiftCertificate(GiftCertificateDto giftDto){
        GiftCertificate gift = modelMapper.map(giftDto, GiftCertificate.class);
        gift.setCreateDate(LocalDateTime.now());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
    }

    @Transactional
    public void deleteGiftCertificate(int id){
        giftCertificateDAO.deleteById(id);
    }

    @Transactional
    public Page<GiftCertificateDto> findAll(Pageable pageable){
        return giftCertificateDAO.findAll(pageable)
                .map(c -> modelMapper.map(c, GiftCertificateDto.class));
    }

    @Transactional
    public GiftCertificateDto findById(int id){
        Optional<GiftCertificate> certificateToFind;
        GiftCertificateDto giftCertificateDto;
        certificateToFind = giftCertificateDAO.findById(id);
        giftCertificateDto = certificateToFind.map(certificate -> modelMapper.map(certificate, GiftCertificateDto.class)).get();
        return giftCertificateDto;
    }

    @Transactional
    public void updateGiftCertificate(GiftCertificateDto giftDto){
        GiftCertificate gift = modelMapper.map(giftDto, GiftCertificate.class);
        gift.setCreateDate(giftCertificateDAO.findById(gift.getId()).get().getCreateDate());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
    }

    @Transactional
    public void updateGiftCertificatePrice(int idGift, double price){
        GiftCertificate gift = giftCertificateDAO.getById(idGift);
        gift.setLastUpdateDate(LocalDateTime.now());
        gift.setPrice(price);
        giftCertificateDAO.save(gift);
    }



   /* @Transactional
    public void updateGiftCertificatePrice(int idGift, double price){
        giftCertificateDAO.(idGift, price);
    }*/

    /*public Collection<GiftCertificate> allGiftCertificate(int page, int size){
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
    }*/
}
