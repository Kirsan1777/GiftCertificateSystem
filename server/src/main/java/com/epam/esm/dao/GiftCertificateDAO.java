package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface gift certificate DAO.
 */
@Repository
public interface GiftCertificateDAO extends JpaRepository<GiftCertificate, Integer>, JpaSpecificationExecutor<Tag> {
    int removeAllById(long id);
    //Page<Tag> findAll(Pageable pageable);
    Tag findByName(String name);
}