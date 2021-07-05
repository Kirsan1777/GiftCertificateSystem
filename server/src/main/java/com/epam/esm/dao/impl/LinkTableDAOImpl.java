package com.epam.esm.dao.impl;


import com.epam.esm.dao.GiftTagMapper;
import com.epam.esm.dao.LinkTableDAO;
import com.epam.esm.dao.query.SqlManyToManyQuery;
import com.epam.esm.model.GiftTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class for realise interface LinkTableDAO
 */
@Component
public class LinkTableDAOImpl implements LinkTableDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinkTableDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTagToGiftCertificate(long idTag, int idGift) {
        jdbcTemplate.update(SqlManyToManyQuery.ADD_A_GIFT_TAG, idTag, idGift);
    }

    public List<GiftTag> getConcatenatedTables(String sortBy) {
        return jdbcTemplate.query("SELECT t.name, c.name, c.price, c.duration, c.description, c.create_date, c.last_update_date" +
                " FROM many_to_many gct JOIN tag t ON gct.id_tag = t.id JOIN gift_certificate c ON gct.id_certificate = c.id" + sortBy, new GiftTagMapper());
    }
}
