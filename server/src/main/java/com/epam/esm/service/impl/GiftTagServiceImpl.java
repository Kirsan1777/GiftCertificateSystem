package com.epam.esm.service.impl;

import com.epam.esm.dao.LinkTableDAO;
import com.epam.esm.dao.impl.LinkTableDAOImpl;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class for realise interface GiftTagService
 */
@Component
public class GiftTagServiceImpl implements GiftTagService {
    private final TagDAOImpl tagDAO;
    private final LinkTableDAOImpl linkTableDAO;

    public GiftTagServiceImpl(TagDAOImpl tagDAO, LinkTableDAOImpl linkTableDAO) {
        this.tagDAO = tagDAO;
        this.linkTableDAO = linkTableDAO;
    }


    public void addTagToGiftCertificate(String nameTag, int idGiftCertificate) {
        Tag tag = new Tag();
        if (tagDAO.readOneTagByName(nameTag) == null) {
            tag.setName(nameTag);
            tagDAO.addTag(tag);;
        }
        long id = tagDAO.readOneTagByName(nameTag).getId();
        linkTableDAO.addTagToGiftCertificate(id, idGiftCertificate);
        //linkTable.addTagToGift(id, idGiftCertificate);
    }

}
