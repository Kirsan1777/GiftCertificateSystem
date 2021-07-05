package com.epam.esm.controller;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

/**
 * Class of tag controller to handle requests and response
 */
@RestController
@RequestMapping("/tag")
@EnableAutoConfiguration
public class TagController {
    private static final String ASC = " ORDER BY name ASC";
    private static final String TAG_PAGE = "tag/firstPage";
    private static final String TAG_UPDATE_PAGE = "tag/change";
    private static final String TAG_REDIRECT_PAGE = "redirect:/tag/firstPage";
    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService, TagDAO tagDAO){
        this.tagService = tagService;
    }

    @GetMapping("/firstPage")
    public Iterable<Tag> firstPage() {
        return tagService.allTags(ASC);
    }

    @GetMapping("/{id}")
    public Tag show(@PathVariable("id") int id) {
        return tagService.findById(id);
    }

    @PostMapping
    public Iterable<Tag> createTag(@ModelAttribute("tag") Tag tag){
        tagService.addTag(tag);
        return tagService.allTags(ASC);
    }

    @DeleteMapping("/{id}")
    public Iterable<Tag> delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return tagService.allTags(ASC);
    }

}