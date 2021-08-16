package com.epam.esm.dao.impl;

import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserDAOImpl {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<User> allUsers(int page, int size){
        CriteriaQuery<User> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public User getOneUserById(int id){
        return entityManager.find(User.class, id);
    }

    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }

}
