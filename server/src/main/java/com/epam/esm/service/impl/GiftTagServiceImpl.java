package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftTagService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class for realise interface GiftTagService
 */
@Component
public class GiftTagServiceImpl implements GiftTagService {
    private final TagDAO tagDAO;


    public GiftTagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Transactional
    public void addTagToGiftCertificate(String nameTag, int idGiftCertificate) {
        Tag tag = new Tag();
        if (tagDAO.findTagByName(nameTag) == null) {
            tag.setName(nameTag);
            tagDAO.save(tag);;
        }
        int id = tagDAO.findTagByName(nameTag).getId();
        tagDAO.addTagToGift(id, idGiftCertificate);
    }

}

