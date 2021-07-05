package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.HibernateSessionFactoryUtil;
import com.epam.esm.dao.query.SqlGiftCertificateQuery;
import com.epam.esm.model.GiftCertificate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for realise interface GiftCertificateDAO
 */
@Repository
public class GiftCertificateDAOImpl {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GiftCertificate> allCertificate(String sort) {
        return jdbcTemplate.query(SqlGiftCertificateQuery.SELECT_ALL_CERTIFICATE + sort, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    /*public GiftCertificate readOneGiftById(int id) {
        return jdbcTemplate.query(SqlGiftCertificateQuery.SELECT_CERTIFICATE_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findAny().orElse(null);
    }*/

    public GiftCertificate readOneGiftById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(GiftCertificate.class, id);
    }

    /*public int addCertificate(GiftCertificate certificate) {
        return jdbcTemplate.update(SqlGiftCertificateQuery.ADD_CERTIFICATE, certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate(), certificate.getName());
    }*/

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

    public int updateCertificate(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(SqlGiftCertificateQuery.UPDATE_CERTIFICATE,
                giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getName(), giftCertificate.getId());
    }

    public int updateCertificatePrice(int idGift, double price) {
        return jdbcTemplate.update(SqlGiftCertificateQuery.UPDATE_PRICE_CERTIFICATE,
                price, LocalDateTime.now(), idGift);
    }

}
