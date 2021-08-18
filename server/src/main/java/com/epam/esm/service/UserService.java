package com.epam.esm.service;

import com.epam.esm.model.Tag;
import com.epam.esm.model.User;

import java.util.List;

/**
 * The interface user service.
 */
public interface UserService {

    /**
     * Method for add one user
     *
     * @param user the user entity
     */
    void addUser(User user);

    /**
     * Method for getting all users
     *
     * @param page the number of page
     * @param size the number object for view
     */
    List<User> getAllUsers(int page, int size);

    /**
     * Method for getting one user by id
     *
     * @param idUser the user id
     */
    User getUserById(int idUser);

    /**
     * Method for getting most widely used tag of a user with the highest cost
     *
     * @param id the user id
     */
    List<Tag> getTheMostExpensiveTag(int id);
}
