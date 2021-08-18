package com.epam.esm.service;

import com.epam.esm.model.Tag;

import java.util.List;

/**
 * The interface tag service.
 */
public interface TagService {
    /**
     * Method for get all tags
     *
     * @param page the param for input number page
     * @param size count of object for view
     */
    List<Tag> viewAll(int page, int size);

    /**
     * Method for delete tag
     *
     * @param idTag the tag id
     */
    void deleteTag(int idTag);


    /**
     * Method for add tag
     *
     * @param tag the tag
     */
    void addTag(Tag tag);

    /**
     * Method for get one tag by name
     *
     * @param name the tag name
     */
    Tag findByName(String name);

    /**
     * Method for get one tag by id
     *
     * @param id the tag id
     */
    Tag findById(int id);

}
