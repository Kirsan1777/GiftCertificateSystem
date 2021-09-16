package com.epam.esm;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagTest {
    @Mock
    private static TagDAO tagDAO;

    TagService service;

    private static Tag tag = new Tag("Tag");
    private static TagDto tagDto = new TagDto("Tag", 1);
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @BeforeEach
    void init(){
        service = new TagServiceImpl(tagDAO, modelMapper());
    }

    @Test
    void addTag(){
        when(tagDAO.save(any(Tag.class))).thenReturn(tag);
        service.addTag(tagDto);
        verify(tagDAO, times(2)).save(any(Tag.class));
    }

    @Test
    void deleteTag(){
        service.deleteTag(1);
        verify(tagDAO, times(1)).deleteById(any(int.class));
    }

    @Test
    void findByIdTag(){
        Optional<Tag> tagToFind = Optional.of(tag);
        TagDto tagDto = tagToFind.map(tag -> modelMapper().map(tag, TagDto.class)).get();
        when(tagDAO.findById(any(int.class))).thenReturn(tagToFind);
        assertEquals(tagDto, service.findById(1));
    }
}
