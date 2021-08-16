package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.LinkTableDAOImpl;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * The class for realise interface GiftTagService
 */
@Component
public class GiftTagServiceImpl {
    @Autowired
    private TagDAOImpl tagDAO;


    @Autowired
    private LinkTableDAOImpl linkTableDAO;

    public void addTagToGiftCertificate(String nameTag, int idGiftCertificate) {
        Tag tag = new Tag();
        if (tagDAO.readOneTagByName(nameTag) == null) {
            tag.setName(nameTag);
            tagDAO.addTag(tag);;
        }
        long id = tagDAO.readOneTagByName(nameTag).getId();
        linkTableDAO.addTagToGiftCertificate(id, idGiftCertificate); //add method for many-to-many
    }

    public List<GiftTag> getConcatenatedTablesByIdGiftCertificate(int idCertificate){
        return linkTableDAO.getConcatenatedTablesByIdGiftCertificate(idCertificate);
    }

}
