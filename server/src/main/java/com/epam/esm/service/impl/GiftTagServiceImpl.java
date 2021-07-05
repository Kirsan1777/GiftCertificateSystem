package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.LinkTableDAOImpl;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * The class for realise interface GiftTagService
 */
@Component
public class GiftTagServiceImpl implements GiftTagService {
    @Autowired
    private TagDAOImpl tagDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TransactionTemplate template;

    @Autowired
    private LinkTableDAOImpl linkTableDAO;

    public void addTagToGiftCertificate(String nameTag, int idGiftCertificate) {
        if (tagDAO.readOneTagByName(nameTag) == null) {
            tagDAO.addTag(nameTag);
        }
        long id = tagDAO.readOneTagByName(nameTag).getId();
        linkTableDAO.addTagToGiftCertificate(id, idGiftCertificate); //add method for many-to-many
    }

    @Transactional
    public void addTagToGiftCertificateTransaction(String nameTag, int idGiftCertificate) {
        TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
        int checkpoint = 0;
        Object savepoint = status.createSavepoint();
        if (tagDAO.readOneTagByName(nameTag) == null) {
            checkpoint = tagDAO.addTag(nameTag);
        }
        int id = tagDAO.readOneTagByName(nameTag).getId();
        linkTableDAO.addTagToGiftCertificate(id, idGiftCertificate); //add method for many-to-many
        if(checkpoint == 0){
            status.rollbackToSavepoint(savepoint);
        }
    }
}
