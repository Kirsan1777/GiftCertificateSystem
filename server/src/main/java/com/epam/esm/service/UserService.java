package com.epam.esm.service;

import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface user service.
 */
public interface UserService {

    /**
     * Method for add one user
     *
     * @param newUser the class with information about user
     */
    String createUser(UserDto newUser);

    /**
     * Method for getting all users
     *
     * @param pageable the setting for class pageable
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Method for getting one user by id
     *
     * @param idUser the user id
     */
    UserDto getUserById(int idUser);

    /**
     * Method for getting most widely used tag of a user with the highest cost
     *
     * @param id the user id
     * @param pageable the setting for class pageable
     */
    Page<UserOrderDto> getTheMostExpensiveOrder(int id, Pageable pageable);

    /**
     * Method for getting most used user tag
     *
     * @param idUser the user id
     */
    List<Tag> findMostUsedUserTag(int idUser);

    /**
     * Method for getting user by name
     *
     * @param username the user name
     */
    User loadUserByUsername(String username);
}
