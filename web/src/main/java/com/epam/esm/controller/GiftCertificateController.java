package com.epam.esm.controller;


import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.*;


/**
 * Class of gift certificate controller to handle requests and response
 */
@RestController
@RequestMapping("/certificate")
@EnableAutoConfiguration
public class GiftCertificateController {

    //todo added exception handler +
    //todo added validator
    //todo check secured annotation and preauthorize +

    private final GiftTagServiceImpl giftTagService;
    private final GiftCertificateServiceImpl giftCertificate;

    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificate, GiftTagServiceImpl giftTagService) {
        this.giftCertificate = giftCertificate;
        this.giftTagService = giftTagService;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto show(@PathVariable("id") int id) {
        return HateoasManager.addLinksToGiftCertificate(giftCertificate.findById(id));
    }

    @GetMapping
    public Page<GiftCertificateDto> showCertificates(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GiftCertificateDto> pageCertificates = giftCertificate.findAll(pageable);
        return HateoasManager.addLinksToListGiftCertificate(pageCertificates);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAuthority('read')")
    public List<GiftCertificateDto> filterCertificates(@RequestParam(value = "tag") List<String> tagNames) {
        return giftCertificate.filter(tagNames);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<GiftCertificate> addGift(@Valid @RequestBody GiftCertificateDto gift) {
        giftCertificate.addGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add/tag/{idGift}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> addTagToGift(@RequestBody TagDto newTag, @PathVariable("idGift") int idGiftCertificate) {
        giftTagService.addTagToGiftCertificate(newTag.getName(), idGiftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> updateGift(@PathVariable("id") int id, @Valid @RequestBody GiftCertificateDto gift) {
        gift.setId(id);
        giftCertificate.updateGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("price/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> updateGiftPrice(@RequestBody GiftCertificateDto gift, @PathVariable("id") int id) {
        giftCertificate.updateGiftCertificatePrice(id, gift.getPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Object> deleteGift(@PathVariable("id") int id) {
        giftCertificate.deleteGiftCertificate(id);
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

    /*@GetMapping("/tag")
    @PreAuthorize("hasAuthority('developers:read')")
    public Optional<Specification<GiftCertificate>> showGiftCertificateByTag(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(value = "tag") List<String> tagNames) {
        Optional<Specification<GiftCertificate>> pageCertificates = giftCertificate.defineTagSpecification(tagNames);
        return pageCertificates;
    }*/