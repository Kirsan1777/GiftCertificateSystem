package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.specification.GiftCertificateSpecification;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The class for realise interface GiftCertificateService
 */
@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

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

    @Override
    @Transactional
    public void addGiftCertificate(GiftCertificateDto giftDto){
        GiftCertificate gift = modelMapper.map(giftDto, GiftCertificate.class);
        gift.setCreateDate(LocalDateTime.now());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
    }

    @Transactional
    public GiftCertificate addGiftCertificateWithResult(GiftCertificateDto giftDto){
        GiftCertificate gift = modelMapper.map(giftDto, GiftCertificate.class);
        gift.setCreateDate(LocalDateTime.now());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
        return gift;
    }

    @Override
    @Transactional
    public void deleteGiftCertificate(int id){
        giftCertificateDAO.deleteById(id);
    }

    @Override
    @Transactional
    public Page<GiftCertificateDto> findAll(Pageable pageable){
        return giftCertificateDAO.findAll(pageable)
                .map(c -> modelMapper.map(c, GiftCertificateDto.class));
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(int id){
        Optional<GiftCertificate> certificateToFind;
        GiftCertificateDto giftCertificateDto;
        certificateToFind = giftCertificateDAO.findById(id);
        giftCertificateDto = certificateToFind.map(certificate -> modelMapper.map(certificate, GiftCertificateDto.class)).get();
        return giftCertificateDto;
    }

    @Override
    @Transactional
    public void updateGiftCertificate(GiftCertificateDto giftDto){
        GiftCertificate gift = modelMapper.map(giftDto, GiftCertificate.class);
        gift.setCreateDate(giftCertificateDAO.findById(gift.getId()).get().getCreateDate());
        gift.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.save(gift);
    }

    @Override
    @Transactional
    public void updateGiftCertificatePrice(int idGift, double price){
        GiftCertificate gift = giftCertificateDAO.getById(idGift);
        gift.setLastUpdateDate(LocalDateTime.now());
        gift.setPrice(price);
        giftCertificateDAO.save(gift);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> filter(List<String> tags) {
        List<GiftCertificate> filteredCertificates;
        Optional<Specification<GiftCertificate>> currentSpecification = defineSpecification(tags);
        filteredCertificates = currentSpecification.map(giftCertificateSpecification ->
                giftCertificateDAO.findAll(giftCertificateSpecification, Sort.by(Sort.DEFAULT_DIRECTION, "id"))).orElseGet(() ->
                giftCertificateDAO.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "id")));
        return filteredCertificates.stream()
                .map(c -> modelMapper.map(c, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    private Optional<Specification<GiftCertificate>> defineSpecification(
            List<String> tagNames) {
        Optional<Specification<GiftCertificate>> currentSpecification = Optional.empty();
        currentSpecification = defineTagSpecification(tagNames);
        return currentSpecification;
    }

    private Optional<Specification<GiftCertificate>> defineTagSpecification(List<String> tagNames) {
        Optional<Specification<GiftCertificate>> tagSpecification = Optional.empty();
        if (!tagNames.isEmpty()) {
            tagSpecification = Optional.of(Specification
                    .where(GiftCertificateSpecification.filterCertificatesByTag(tagNames.get(0))));
            for (int i = 1; i < tagNames.size(); i++) {
                tagSpecification = Optional.of(tagSpecification.get().and(Specification
                        .where(GiftCertificateSpecification.filterCertificatesByTag(tagNames.get(i)))));
            }
        }
        return tagSpecification;
    }

}
