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

    @Query(value ="select * from user_order where cost = " +
            "(select max(cost) from user_order having id_user = :userId) and id_user = :userId",
            nativeQuery = true
    )
    Page<UserOrder> findMostExpensiveUserOrder(@Param("userId") int userId, Pageable pageable);

    Page<UserOrder> findAll(Pageable pageable);

    Page<UserOrder> findUserOrderByIdUser(Pageable pageable, int id);

    UserOrder findUserOrderByIdCertificate(int id);

}
