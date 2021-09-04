package com.epam.esm.dao;

import com.epam.esm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface user DAO.
 */
@Repository
public interface UserDAO extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Method for getting all users
     *
     * @param pageable the setting for class pageable
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Method for getting user by name
     *
     * @param username the username
     */
    User findByUsername(String username);

    /**
     * Method for getting user by id
     *
     * @param id the user id
     */
    Optional<User> findById(int id);

    /**
     * Method for getting user by id
     *
     * @param idUser the user id
     */
    User findUserById(int idUser);
}
