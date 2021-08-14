package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface gift certificate DAO.
 */
@Repository
public interface GiftCertificateDAO extends CrudRepository<GiftCertificate, Integer> {

}