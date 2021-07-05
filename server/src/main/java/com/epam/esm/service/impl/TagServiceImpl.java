package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class for realise interface TagService
 */
@Component
public class TagServiceImpl {
    private TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public TagServiceImpl() {

    }

    public Iterable<Tag> allTags(String sort) {
        return tagDAO.findAll();
    }

    public void deleteTag(int idTag) {
         tagDAO.deleteById(idTag);
    }

    public void addTag(Tag tag) {
        tagDAO.addTag(tag.getName());
    }

    public Tag findByName(String name) {
        return tagDAO.readOneTagByName(name);
    }

    public Tag findById(int id) {
        return tagDAO.readOneTagById(id);
    }

}
