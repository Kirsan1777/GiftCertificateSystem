package com.epam.esm.controller;


import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class of gift certificate controller to handle requests and response
 */
@RestController
@RequestMapping("/certificate")
@EnableAutoConfiguration
public class GiftCertificateController {

    private final GiftTagServiceImpl giftTagService;
    private final GiftCertificateServiceImpl giftCertificate;

    //private static final String ACCEPT_READ = 'developer:read';
    //private static final String ACCEPT_WRITE = "developer:write";

    /*Use nouns to represent resources
    RESTful URI should refer to a resource that is a thing (noun) instead of
    referring to an action (verb) because nouns have properties which verbs do not have – similar to resources have attributes.
     Some examples of a resource are:
    Users of the system
    User Accounts
    Network Devices etc.
    and their resource URIs can be designed as below
    http://api.example.com/device-management/managed-devices
    http://api.example.com/device-management/managed-devices/{device-id} 
    http://api.example.com/user-management/users/
    http://api.example.com/user-management/users/{id}
    */
    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificate, GiftTagServiceImpl giftTagService) {
        this.giftCertificate = giftCertificate;
        this.giftTagService = giftTagService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public GiftCertificateDto show(@PathVariable("id") int id) {
        return HateoasManager.addLinksToGiftCertificate(giftCertificate.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('developers:read')")
    public Page<GiftCertificateDto> showCertificates(@PageableDefault(
            sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GiftCertificateDto> pageCertificates = giftCertificate.findAll(pageable);
        return HateoasManager.addLinksToListGiftCertificate(pageCertificates);

    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<GiftCertificate> addGift(@Valid @RequestBody GiftCertificateDto gift) {
        giftCertificate.addGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add/tag/{idGift}", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Object> addTagToGift(@RequestBody Tag newTag, @PathVariable("idGift") int idGiftCertificate) {
        giftTagService.addTagToGiftCertificate(newTag.getName(), idGiftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Object> updateGift(@PathVariable("id") int id, @Valid @RequestBody GiftCertificateDto gift) {
        gift.setId(id);
        giftCertificate.updateGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("price/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Object> updateGiftPrice(@RequestBody GiftCertificateDto gift, @PathVariable("id") int id) {
        giftCertificate.updateGiftCertificatePrice(id, gift.getPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
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
    /*@GetMapping("/several-tags") -
    public Collection<GiftCertificate> getGiftBySeveralTags(@RequestParam(value = "tag") List<String> tagsName) {
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificateByTags(tagsName));
    }

    @GetMapping("/{id}") +
    public GiftCertificate show(@PathVariable("id") int id) {
        return giftCertificate.(id);
    }

    @PostMapping("/add") +
    public ResponseEntity<GiftCertificate> addGift(@RequestBody GiftCertificate gift) {
        giftCertificate.addGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-tag-to-gift/{idTag}", method = RequestMethod.POST) +
    public Collection<GiftCertificate> addTagToGift(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                    @RequestBody Tag newTag, @PathVariable("idTag") int idGiftCertificate) {
        giftTagService.addTagToGiftCertificate(newTag.getName(), idGiftCertificate);
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate(page, size));
    }

    @DeleteMapping("/{id}") +
    public ResponseEntity<Object> deleteGift(@PathVariable("id") int id) {
        giftCertificate.deleteGiftCertificate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}") +
    public ResponseEntity<Object> updateGift(@PathVariable("id") int id, @RequestBody GiftCertificate gift) {
        gift.setId(id);
        giftCertificate.updateGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("price/{id}") +
    public Collection<GiftCertificate> updateGiftPrice(@RequestBody GiftCertificate gift, @PathVariable("id") int id,
                                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        giftCertificate.updateGiftCertificatePrice(id, gift.getPrice());
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate(page, size));
    }*/

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Exception in gift certificate controller \nexception : " + ex.getMessage() + "\n" + HttpStatus.FORBIDDEN, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}