package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for realise interface GiftCertificateDAO
 */
@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    private final EntityManager entityManager;

    private static final String UPDATE_PRICE_IN_GIFT_CERTIFICATE = " UPDATE GiftCertificate gift_certificate SET gift_certificate.price = :price, gift_certificate.lastUpdateDate = :lastUpdateDate WHERE gift_certificate.id = :id";
    private static final String UPDATE_GIFT_CERTIFICATE = " UPDATE GiftCertificate gift_certificate SET gift_certificate.description = :description, gift_certificate.duration = :duration, gift_certificate.name = :name, gift_certificate.price = :price, gift_certificate.createDate = :create_date, gift_certificate.lastUpdateDate = :lastUpdateDate WHERE gift_certificate.id = :id";
    private static final String FIND_GIFT_CERTIFICATE_BY_TAGS = "SELECT * from gift_certificate where id in (SELECT gift_certificate.id FROM gift_certificate  JOIN many_to_many mtm on gift_certificate.id = mtm.id_certificate JOIN tag t on t.id = mtm.id_tag where t.name = ";
    private static final String ADD_TAG_TO_QUERY = " AND gift_certificate.id in (SELECT gift_certificate.id FROM gift_certificate  JOIN many_to_many mtm on gift_certificate.id = mtm.id_certificate JOIN tag t on t.id = mtm.id_tag where t.name = ";

    @Autowired
    public GiftCertificateDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<GiftCertificate> allCertificate(int page, int size) {
        CriteriaQuery<GiftCertificate> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public GiftCertificate readOneGiftById(int id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Transactional
    public List<GiftCertificate> allGiftCertificateByTags(List<String> tagsName){
     String sqlQuery = FIND_GIFT_CERTIFICATE_BY_TAGS;
        if(tagsName.size()>1){
            sqlQuery = sqlQuery.concat("'"+tagsName.get(0)+"'");
            for(int i = 1; i<tagsName.size(); i++){
                sqlQuery = sqlQuery.concat(ADD_TAG_TO_QUERY).concat("'"+tagsName.get(i)+"'");
            }
            for (int i = 0; i<tagsName.size(); i++){
                sqlQuery = sqlQuery.concat(")");
            }
        } else {
            sqlQuery = sqlQuery.concat("'"+tagsName.get(0)+"'").concat(")");
        }
        return entityManager.createNativeQuery(sqlQuery, GiftCertificate.class).getResultList();
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
        entityManager.createQuery(UPDATE_GIFT_CERTIFICATE).setParameter("id", giftCertificate.getId()).setParameter("price", giftCertificate.getPrice())
                .setParameter("create_date", giftCertificate.getCreateDate()).setParameter("duration", giftCertificate.getDuration())
                .setParameter("description", giftCertificate.getDescription()).setParameter("name", giftCertificate.getName())
                .setParameter("lastUpdateDate", LocalDateTime.now()).executeUpdate();
    }

    @Transactional
    public void updateCertificatePrice(int idGift, double price) {
        entityManager.createQuery(UPDATE_PRICE_IN_GIFT_CERTIFICATE).setParameter("id", idGift).setParameter("price", price)
                .setParameter("lastUpdateDate", LocalDateTime.now()).executeUpdate();
    }

}
