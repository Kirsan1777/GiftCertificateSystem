package com.epam.esm.dao;

import com.epam.esm.model.UserOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderDAO extends CrudRepository<UserOrder, Long> {
}
