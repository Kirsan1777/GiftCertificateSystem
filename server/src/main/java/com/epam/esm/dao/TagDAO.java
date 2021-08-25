package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface tag DAO.
 */
@Repository
public interface TagDAO extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
    @Modifying
    @Query(value = "INSERT INTO many_to_many(id_tag, id_certificate) VALUES (:idTag, :idCertificate)",
            nativeQuery = true)
    void addTagToGift(@Param("idTag") int idTag, @Param("idCertificate") int idCertificate);

    Page<Tag> findAll(Pageable pageable);

    Tag findTagByName(String name);

    @Query(value = "SELECT t.id, t.name FROM many_to_many gct" +
            " JOIN tag t ON gct.id_tag = t.id " +
            "JOIN gift_certificate c ON gct.id_certificate = c.id  " +
            "join user_order uo on gct.id_certificate = uo.id_certificate " +
            "where id_user = :idUser group by t.name order by count(t.name) desc limit 1",
            nativeQuery = true)
    List<Tag> findMostUsedUserTag(@Param("idUser") int idUser);
}
