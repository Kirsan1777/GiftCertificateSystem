package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.query.SqlTagQuery;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class for realise interface TagDAO
 */
@Component
public class TagDAOImpl{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> allTags(String sort){
        return jdbcTemplate.query(SqlTagQuery.SELECT_ALL_TAG + sort, new BeanPropertyRowMapper<>(Tag.class));
    }

    public int addTag(String name) {
        return jdbcTemplate.update(SqlTagQuery.ADD_TAG, name);
    }

    public int deleteTag(int idTag) {
        return jdbcTemplate.update(SqlTagQuery.DELETE_TAG, idTag);
    }

    public Tag readOneTagByName(String name){
        return jdbcTemplate.query(SqlTagQuery.SELECT_TAG_BY_NAME, new Object[]{name},
                new BeanPropertyRowMapper<>(Tag.class)).stream().findAny().orElse(null);
    }

    public Tag readOneTagById(int id){
        return jdbcTemplate.query(SqlTagQuery.SELECT_TAG_BY_ID, new Object[]{id},
                new BeanPropertyRowMapper<>(Tag.class)).stream().findAny().orElse(null);
    }
}
