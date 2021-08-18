package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
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
     * @param fidt the gift certificate entity
     */
    void addGiftCertificate(GiftCertificate gift);

    /**
     * Method for delete one gift certificate by id
     *
     * @param id the gift certificate id
     */
    void deleteGiftCertificate(int id);

    /**
     * Method for getting all gift certificate
     *
     * @param page the number of page
     * @param size the number object for view
     */
    Collection<GiftCertificate> allGiftCertificate(int page, int size);

    /**
     * Method for update one gift certificate
     *
     * @param gift the gift certificate entity
     */
    void updateGiftCertificate(GiftCertificate gift);

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
    GiftCertificate findGiftById(int id);

    /**
     * Method for getting one gift certificate by id
     *
     * @param tagsName the tag for search gift certificate
     */
    List<GiftCertificate> allGiftCertificateByTags(List<String> tagsName);
}
