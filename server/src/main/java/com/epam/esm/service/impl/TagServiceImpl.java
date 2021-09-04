package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The class for realise interface TagService
 */
@Component
public class TagServiceImpl implements TagService {
    private ModelMapper modelMapper;
    private TagDAO tagDAO;


    @Autowired
    public TagServiceImpl(TagDAO tagDAO, ModelMapper modelMapper) {
        this.tagDAO = tagDAO;
        this.modelMapper = modelMapper;

    }

    @Override
    @Transactional
    public Page<Tag> viewAll(Pageable pageable) {
        return tagDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteTag(int idTag) {
        tagDAO.deleteById(idTag);
    }

    @Override
    @Transactional
    public void addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tagDAO.save(tag);
    }

    @Override
    @Transactional
    public TagDto findById(int id) {
        Optional<Tag> tagToFind = tagDAO.findById(id);
        TagDto tagDto;
        tagDto = tagToFind.map(tag -> modelMapper.map(tag, TagDto.class)).get();
        return tagDto;
    }

}
