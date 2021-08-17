package com.epam.esm.dao;

import com.epam.esm.model.User;

import java.util.List;

/**
 * The interface user DAO.
 */
public interface UserDAO {

    /**
     * Method for getting all users
     *
     * @param page the number of page
     * @param size the number object for view
     */
    List<User> allUsers(int page, int size);

    /**
     * Method for getting one user by id
     *
     * @param id the user id
     */
    User getOneUserById(int id);

    /**
     * Method for add one user
     *
     * @param user the user entity
     */
    void addUser(User user);
}
