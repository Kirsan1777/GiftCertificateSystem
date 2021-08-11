package com.epam.esm.dao.impl;

import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for realise interface GiftCertificateDAO
 */
@Repository
public class GiftCertificateDAOImpl {

    private final EntityManager entityManager;

    private static final String GET_ALL_CERTIFICATE = "SELECT gift_certificate FROM GiftCertificate gift_certificate";
    private static final String UPDATE_PRICE_IN_GIFT_CERTIFICATE = " UPDATE GiftCertificate gift_certificate SET gift_certificate.price = :price, gift_certificate.lastUpdateDate = :lastUpdateDay WHERE gift_certificate.id = :id";

    @Autowired
    public GiftCertificateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<GiftCertificate> allCertificate() {
        return entityManager.createQuery(GET_ALL_CERTIFICATE, GiftCertificate.class).getResultList();
        //List<GiftCertificate> giftCertificateList =  entityManager.createQuery(GET_ALL_CERTIFICATE, GiftCertificate.class).getResultList();
        //return giftCertificateList;
    }

    @Transactional
    public GiftCertificate readOneGiftById(int id) {
        return entityManager.find(GiftCertificate.class, id);
        //GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        //return giftCertificate;
    }

    @Transactional
    public void save(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
    }

    @Transactional
    public void deleteById(int idCertificate) {
        entityManager.remove(entityManager.find(GiftCertificate.class, idCertificate));
    }

    @Transactional
    public void updateCertificate(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
    }

    @Transactional
    public void updateCertificatePrice(int idGift, double price) {
        entityManager.createQuery(UPDATE_PRICE_IN_GIFT_CERTIFICATE).setParameter("id", idGift).setParameter("price", price)
                .setParameter("lastUpdateDate", LocalDateTime.now()).executeUpdate();
    }


    /*public List<GiftCertificate> allCertificate(String sort) {
        return jdbcTemplate.query(SqlGiftCertificateQuery.SELECT_ALL_CERTIFICATE + sort, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    public GiftCertificate readOneGiftById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(GiftCertificate.class, id);
    }


    public void save(GiftCertificate giftCertificate) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(giftCertificate);
        transaction.commit();
        session.close();
    }

    public void deleteById(int idCertificate) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        GiftCertificate giftCertificate = session.get(GiftCertificate.class, idCertificate);
        session.delete(giftCertificate);
        transaction.commit();
        session.close();
    }

    public void updateCertificate(GiftCertificate giftCertificate) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(giftCertificate);
        transaction.commit();
        session.close();
    }

    public int updateCertificatePrice(int idGift, double price) {
        return jdbcTemplate.update(SqlGiftCertificateQuery.UPDATE_PRICE_CERTIFICATE,
                price, LocalDateTime.now(), idGift);
    }*/

}
