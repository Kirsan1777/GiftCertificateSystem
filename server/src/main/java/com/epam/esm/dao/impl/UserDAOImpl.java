package com.epam.esm.dao.impl;

import com.epam.esm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserDAOImpl {

    private final EntityManager entityManager;

    private static final String GET_ALL_USERS = "SELECT users FROM User users";

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<User> allUsers(String sort){
        return entityManager.createQuery(GET_ALL_USERS, User.class).getResultList();
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
