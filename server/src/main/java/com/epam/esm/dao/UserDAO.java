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
    Page<User> findAll(Pageable pageable);
    User findByUsername(String username);
    Optional<User> findById(int id);
    User findUserById(int idUser);
}
