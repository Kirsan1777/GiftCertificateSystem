package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * Class of tag controller to handle requests and response
 */
@RestController
@RequestMapping("/tag")
@EnableAutoConfiguration
public class TagController extends HateoasManager<Tag> {

    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all-tags")
    public Iterable<Tag> showTags(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<Tag> tags = tagService.viewAll(page, size);
        return HateoasManager.addLinksToTags(addPagination(tags, page, size, tagService.getCountOfEntities()));
    }

    @GetMapping("/{id}")
    public Tag show(@PathVariable("id") int id) {
        return HateoasManager.addLinksToTag(tagService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Exception in tag controller \nexception : " + ex.getMessage() + "\n" + HttpStatus.FORBIDDEN, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}