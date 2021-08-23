package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
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
public class TagServiceImpl {
    private ModelMapper modelMapper;
    private TagDAO tagDAO;


    @Autowired
    public TagServiceImpl(TagDAO tagDAO, ModelMapper modelMapper) {
        this.tagDAO = tagDAO;
        this.modelMapper = modelMapper;

    }

    @Transactional
    public Page<Tag> viewAll(Pageable pageable) {
        return tagDAO.findAll(pageable);
    }

    @Transactional
    public void deleteTag(int idTag) {
        tagDAO.deleteById(idTag);
    }

    @Transactional
    public void addTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tagDAO.save(tag);
    }

    @Transactional
    public TagDto findById(int id) {
        Optional<Tag> tagToFind = tagDAO.findById(id);
        TagDto tagDto;
        tagDto = tagToFind.map(tag -> modelMapper.map(tag, TagDto.class)).get();
        return tagDto;
    }

}
