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
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificate, GiftTagServiceImpl giftTagService){
        this.giftCertificate = giftCertificate;
        this.giftTagService = giftTagService;
    }

    @GetMapping("/all-gift-certificates")
    public Collection<GiftCertificate> allGiftCertificates(){
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate());
    }

    @GetMapping("/several-tags/{tagsName}")
    public Collection<GiftCertificate> getGiftBySeveralTags(@PathVariable List<String> tagsName){
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
    public Collection<GiftCertificate> addTagToGift(@RequestBody Tag newTag, @PathVariable("idTag") int idGiftCertificate){
        giftTagService.addTagToGiftCertificate(newTag.getName(), idGiftCertificate);
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate());
    }

    @DeleteMapping("/{id}")
    public Collection<GiftCertificate> deleteGift(@PathVariable("id") int id) {
        giftCertificate.deleteGiftCertificate(id);
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate());
    }

    @PatchMapping
    public Collection<GiftCertificate> updateGift(@RequestBody GiftCertificate gift) {
        giftCertificate.updateGiftCertificate(gift);
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate());
    }

    @PatchMapping("price/{id}")
    public Collection<GiftCertificate> updateGiftPrice(@RequestBody GiftCertificate gift, @PathVariable("id") int id) {
        giftCertificate.updateGiftCertificatePrice(id, gift.getPrice());
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Mistake in gift certificate controller \nexception : " + ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


}