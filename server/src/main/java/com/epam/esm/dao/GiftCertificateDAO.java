package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface gift certificate DAO.
 */
@Repository
public interface GiftCertificateDAO {

    /**
     * Method for getting all gift certificate
     *
     * @param page the number of page
     * @param size the number object for view
     */
    List<GiftCertificate> allCertificate(int page, int size);

    /**
     * Method for getting one gift certificate by id
     *
     * @param id the gift certificate id
     */
    GiftCertificate readOneGiftById(int id);

    /**
     * Method for getting one gift certificate by id
     *
     * @param tagsName the tag for search gift certificate
     */
    List<GiftCertificate> allGiftCertificateByTags(List<String> tagsName);

    /**
     * Method for add one gift certificate
     *
     * @param giftCertificate the gift certificate entity
     */
    void save(GiftCertificate giftCertificate);

    /**
     * Method for delete one gift certificate by id
     *
     * @param idCertificate the gift certificate id
     */
    void deleteById(int idCertificate);

    /**
     * Method for update one gift certificate
     *
     * @param giftCertificate the gift certificate entity
     */
    void updateCertificate(GiftCertificate giftCertificate);

    /**
     * Method for update one gift certificate price only
     *
     * @param idGift the gift certificate id
     * @param price the new price fo gift certificate
     */
    void updateCertificatePrice(int idGift, double price);
}