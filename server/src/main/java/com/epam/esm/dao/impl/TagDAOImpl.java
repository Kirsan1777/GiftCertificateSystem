package com.epam.esm.dao.impl;

import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public List<Tag> allTags( ){
        //return entityManager.createQuery(GET_ALL_TAGS, Tag.class).getResultList();
        CriteriaQuery<Tag> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(0)
                .setMaxResults(100)
                .getResultList();
    }

    @Transactional
    public List<Tag> viewAll(int page, int size){
        CriteriaQuery<Tag> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
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

    public long getCountOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(Tag.class)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}

