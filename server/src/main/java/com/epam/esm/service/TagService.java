package com.epam.esm.service;

import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface tag service.
 */
public interface TagService {

    /**
     * Method for get all tags
     *
     * @param pageable the setting for class pageable
     */
    Page<Tag> viewAll(Pageable pageable);

    /**
     * Method for delete tag
     *
     * @param idTag the tag id
     */
    void deleteTag(int idTag);

    /**
     * Method for add tag
     *
     * @param tagDto the class with information about tag
     */
    void  addTag(TagDto tagDto);

    /**
     * Method for get one tag by id
     *
     * @param id the tag id
     */
    TagDto findById(int id);

}
