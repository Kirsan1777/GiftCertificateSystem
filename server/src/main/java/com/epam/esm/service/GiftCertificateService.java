package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * The interface gift certificate service.
 */
@Service
public interface GiftCertificateService {

    /**
     * Method for add one gift certificate
     *
     * @param giftDto the gift certificate entity
     */
    void addGiftCertificate(GiftCertificateDto giftDto);

    /**
     * Method for delete one gift certificate by id
     *
     * @param id the gift certificate id
     */
    void deleteGiftCertificate(int id);

    /**
     * Method for getting all gift certificate
     *
     * @param pageable the setting for class pageable
     */
    Page<GiftCertificateDto> findAll(Pageable pageable);

    /**
     * Method for update one gift certificate
     *
     * @param giftDto the gift certificate entity
     */
    void updateGiftCertificate(GiftCertificateDto giftDto);

    /**
     * Method for update one gift certificate price only
     *
     * @param idGift the gift certificate id
     * @param price the new price fo gift certificate
     */
    void updateGiftCertificatePrice(int idGift, double price);

    /**
     * Method for getting one gift certificate by id
     *
     * @param id the gift certificate id
     */
    GiftCertificateDto findById(int id);

    /**
     * Method for getting one gift certificate by id
     *
     * @param tags the tags for search gift certificate
     */
    List<GiftCertificateDto> filter(List<String> tags);
}
