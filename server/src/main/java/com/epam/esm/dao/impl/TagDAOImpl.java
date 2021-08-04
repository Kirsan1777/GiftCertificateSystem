package com.epam.esm.dao.impl;

import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Class for realise interface TagDAO
 */
@Repository
public class TagDAOImpl{

    private final EntityManager entityManager;

    private static final String GET_ALL_TAGS = "SELECT tag FROM Tag tag";
    private static final String GET_TAG_BY_NAME = "SELECT tag FROM Tag tag WHERE tag.name = :name";

    @Autowired
    public TagDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Tag> allTags(){
        return entityManager.createQuery(GET_ALL_TAGS, Tag.class).getResultList();
    }

    @Transactional
    public void addTag(Tag tag) {
        entityManager.persist(tag);
    }

    @Transactional
    public void deleteTag(int idTag) {
        entityManager.remove(entityManager.find(Tag.class, idTag));
    }

    @Transactional
    public Tag readOneTagByName(String name){
        return entityManager.createQuery(GET_TAG_BY_NAME, Tag.class).setParameter("name", name).getResultStream()
                .findAny().orElse(null);
    }

    @Transactional
    public Tag readOneTagById(int id){
        return entityManager.find(Tag.class, id);
    }
}

