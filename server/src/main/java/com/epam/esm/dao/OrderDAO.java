package com.epam.esm.dao;

import com.epam.esm.model.userOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderDAO extends CrudRepository<userOrder, Long> {
}
