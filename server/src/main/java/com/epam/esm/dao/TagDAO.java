package com.epam.esm.dao;

import com.epam.esm.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface tag DAO.
 */
@Repository
public interface TagDAO {
    /**
     * Method for get all tags
     *
     * @param page the number of page
     * @param size the number object for view
     */
    List<Tag> viewAll(int page, int size);

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
    void deleteTag(int idTag);

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
