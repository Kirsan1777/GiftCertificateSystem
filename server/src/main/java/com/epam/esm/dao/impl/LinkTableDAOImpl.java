package com.epam.esm.dao.impl;


import com.epam.esm.dao.LinkTableDAO;
import com.epam.esm.model.GiftTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Class for realise interface LinkTableDAO
 */
@Repository
public class LinkTableDAOImpl {

    private final EntityManager entityManager;


    private static final String ADD_A_GIFT_TAG = "INSERT INTO many_to_many(id_tag, id_certificate) VALUES (?, ?)";
    private static final String GET_TAGS_BY_ID_CERTIFICATE = "SELECT t.id, t.name, c.id, c.name, c.price, c.duration, c.description, c.create_date, c.last_update_date FROM many_to_many gct JOIN tag t ON gct.id_tag = t.id JOIN gift_certificate c ON gct.id_certificate = c.id WHERE gct.id_certificate = ?";
    private static final String GET_TAGS_CERTIFICATE = "SELECT t.name, c.name, c.price, c.duration, c.description, c.create_date, c.last_update_date FROM many_to_many gct JOIN tag t ON gct.id_tag = t.id JOIN gift_certificate c ON gct.id_certificate = c.id";
    @Autowired
    public LinkTableDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addTagToGiftCertificate(long idTag, int idGift) {
        entityManager.createNativeQuery(ADD_A_GIFT_TAG).setParameter(1, idTag).setParameter(2, idGift).executeUpdate();
    }

    @Transactional
    public List<GiftTag> getConcatenatedTables(String sort) {
        return entityManager.createNativeQuery(GET_TAGS_CERTIFICATE + sort, GiftTag.class).getResultList();

    }

    @Transactional
    public List<GiftTag> getConcatenatedTablesByIdGiftCertificate(int idCertificate) {
        return entityManager.createNativeQuery(GET_TAGS_BY_ID_CERTIFICATE, GiftTag.class).setParameter(1, idCertificate).getResultList();
    }
}
