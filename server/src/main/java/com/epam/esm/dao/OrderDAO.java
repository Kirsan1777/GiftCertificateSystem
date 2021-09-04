package com.epam.esm.dao;

import com.epam.esm.model.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The interface order DAO.
 */
public interface OrderDAO extends JpaRepository<UserOrder, Integer>, JpaSpecificationExecutor<UserOrder> {

    /**
     * Method for getting most expensive user order
     *
     * @param pageable the setting for class pageable
     * @param userId this is the user id
     */
    @Query(value ="select * from user_order where cost = " +
            "(select max(cost) from user_order having id_user = :userId) and id_user = :userId",
            nativeQuery = true
    )
    Page<UserOrder> findMostExpensiveUserOrder(@Param("userId") int userId, Pageable pageable);

    /**
     * Method for getting all users orders
     *
     * @param pageable the setting for class pageable
     */
    Page<UserOrder> findAll(Pageable pageable);

    /**
     * Method for getting user orders
     *
     * @param pageable the setting for class pageable
     * @param id this is the user id
     */
    Page<UserOrder> findUserOrderByIdUser(Pageable pageable, int id);

}
