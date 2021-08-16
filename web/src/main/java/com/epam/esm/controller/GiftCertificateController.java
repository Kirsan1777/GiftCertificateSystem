package com.epam.esm.controller;


import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Collection;
import java.util.List;


/**
 * Class of gift certificate controller to handle requests and response
 */
@RestController
@RequestMapping("/certificate")
@EnableAutoConfiguration
public class GiftCertificateController extends HateoasManager<GiftCertificate> {

    private final GiftTagServiceImpl giftTagService;
    private final GiftCertificateServiceImpl giftCertificate;


    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificate, GiftTagServiceImpl giftTagService) {
        this.giftCertificate = giftCertificate;
        this.giftTagService = giftTagService;
    }

    @GetMapping("/all-gift-certificates")
    public Collection<GiftCertificate> allGiftCertificates(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate(page, size));
    }

    @GetMapping("/several-tags")
    public Collection<GiftCertificate> getGiftBySeveralTags(@RequestParam(value = "tag") List<String> tagsName) {
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificateByTags(tagsName));
    }

    @GetMapping("/{id}")
    public GiftCertificate show(@PathVariable("id") int id) {
        return HateoasManager.addLinksToGiftCertificate(giftCertificate.findGiftById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<GiftCertificate> addGift(@RequestBody GiftCertificate gift) {
        giftCertificate.addGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/add-tag-to-gift/{idTag}", method = RequestMethod.POST)
    public Collection<GiftCertificate> addTagToGift(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                                                    @RequestBody Tag newTag, @PathVariable("idTag") int idGiftCertificate) {
        giftTagService.addTagToGiftCertificate(newTag.getName(), idGiftCertificate);
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGift(@PathVariable("id") int id) {
        giftCertificate.deleteGiftCertificate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateGift(@PathVariable("id") int id, @RequestBody GiftCertificate gift) {
        gift.setId(id);
        giftCertificate.updateGiftCertificate(gift);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("price/{id}")
    public Collection<GiftCertificate> updateGiftPrice(@RequestBody GiftCertificate gift, @PathVariable("id") int id,
                                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                       @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        giftCertificate.updateGiftCertificatePrice(id, gift.getPrice());
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate(page, size));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Exception in gift certificate controller \nexception : " + ex.getMessage() + "\n" + HttpStatus.FORBIDDEN, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}