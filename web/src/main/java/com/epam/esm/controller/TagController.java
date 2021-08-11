package com.epam.esm.controller;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class of tag controller to handle requests and response
 */
@RestController
@RequestMapping("/tag")
@EnableAutoConfiguration
public class TagController extends HateoasManager<Tag>{
    private static final String ASC = " ORDER BY name ASC";
    private static final String TAG_PAGE = "tag/firstPage";
    private static final String TAG_UPDATE_PAGE = "tag/change";
    private static final String TAG_REDIRECT_PAGE = "redirect:/tag/firstPage";
    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService){
        this.tagService = tagService;
    }

    @GetMapping("/page")
    public Collection<Tag> showTags(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<Tag> tags = tagService.viewAll(page, size);
        //manageTagLinks(tags, pageable);
        return addPagination(tags, page, size, tagService.getCountOfEntities());
    }

    @GetMapping("/firstPage")
    public Iterable<Tag> firstPage() {
        return HateoasManager.addLinksToTag(tagService.allTags());
    }

    @GetMapping("/{id}")
    public Tag show(@PathVariable("id") int id) {
        return tagService.findById(id);
    }

    @PostMapping
    public Iterable<Tag> createTag(@ModelAttribute("tag") Tag tag){
        tagService.addTag(tag);
        return tagService.allTags();
    }

    @DeleteMapping("/{id}")
    public Iterable<Tag> delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return tagService.allTags();
    }

}