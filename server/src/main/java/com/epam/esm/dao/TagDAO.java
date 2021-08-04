package com.epam.esm.dao;

import com.epam.esm.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface tag DAO.
 */
@Repository
public interface TagDAO extends CrudRepository<Tag, Integer> {
    /**
     * Method for get all tags
     *
     * @param sort the param for sort table
     */
    //List<Tag> allTags(String sort);

    /**
     * Method for add tag
     *
     * @param name the tag name
     */
    void addTag(Tag name);

    /**
     * Method for delete tag
     *
     * @param idTag the tag id
     */
    int deleteTag(int idTag);

    /**
     * Method for get one tag by name
     *
     * @param name the tag name
     */
    Tag readOneTagByName(String name);

    /**
     * Method for get one tag by id
     *
     * @param id the tag id
     */
    Tag readOneTagById(int id);
}
