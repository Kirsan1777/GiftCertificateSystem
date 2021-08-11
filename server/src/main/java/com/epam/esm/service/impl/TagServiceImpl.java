package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.print.Pageable;
import java.util.List;

/**
 * The class for realise interface TagService
 */
@Component
public class TagServiceImpl {
    private TagDAOImpl tagDAOImpl;

    public TagServiceImpl() {
    }

    @Autowired
    public TagServiceImpl(TagDAOImpl tagDAOImpl) {
        this.tagDAOImpl = tagDAOImpl;
    }

    public Iterable<Tag> allTags() {
        return tagDAOImpl.allTags();
    }

    public List<Tag> viewAll(int page, int size) {
        return tagDAOImpl.viewAll(page, size);
    }

    public void deleteTag(int idTag) {
        tagDAOImpl.deleteTag(idTag);
    }

    public void addTag(Tag tag) {
        tagDAOImpl.addTag(tag);
    }

    public Tag findByName(String name) {
        return tagDAOImpl.readOneTagByName(name);
    }

    public Tag findById(int id) {
        return tagDAOImpl.readOneTagById(id);
    }

    public long getCountOfEntities(){
        return tagDAOImpl.getCountOfEntities();
    }


    /*public Page<Tag> allTags(Pageable pageable) {
        return tagDAOImpl.viewTag(pageable);
    }*/

}
