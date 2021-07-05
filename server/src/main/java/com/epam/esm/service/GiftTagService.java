package com.epam.esm.service;

import org.springframework.stereotype.Service;

/**
 * The interface concatenated tables service.
 */
@Service
public interface GiftTagService {
    /**
     * Method for linking tag and gift certificate
     *
     * @param nameTag the tag name
     * @param idGiftCertificate the gift certificate id
     */
    void addTagToGiftCertificate(String nameTag, int idGiftCertificate);
}
