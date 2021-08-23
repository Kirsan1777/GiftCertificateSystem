package com.epam.esm.dao;

import com.epam.esm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface user DAO.
 */
public interface UserDAO extends JpaRepository<User, Integer> {
    Page<User> findAll(Pageable pageable);
}
