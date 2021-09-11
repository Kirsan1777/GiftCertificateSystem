package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/tags")
    @PreAuthorize("hasAuthority('read')")
    public Iterable<Tag> showTags(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Tag> tags = tagService.viewAll(pageable);
        return HateoasManager.addLinksToTags(tags);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public TagDto show(@PathVariable("id") int id) {
        return HateoasManager.addLinksToTag(tagService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> createTag(@Valid @RequestBody TagDto tag) {
        tagService.addTag(tag);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}