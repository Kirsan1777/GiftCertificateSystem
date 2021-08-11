package com.epam.esm.controller;


import com.epam.esm.dao.impl.LinkTableDAOImpl;
import com.epam.esm.hateoas.HateoasManager;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Class of gift certificate controller to handle requests and response
 */
@RestController
@RequestMapping("/certificate")
@EnableAutoConfiguration
public class GiftCertificateController {
    private static final String ASC = " ORDER BY name ASC";
    private static final String CERTIFICATE_PAGE = "certificate/secondPage";
    private static final String CERTIFICATE_UPDATE_PAGE = "certificate/update";
    private static final String CERTIFICATE_REDIRECT_PAGE = "redirect:/certificate/secondPage";


    private final GiftTagServiceImpl giftTagService;
    private final GiftCertificateServiceImpl giftCertificate;
    private final LinkTableDAOImpl linkTableDAO;

    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificate, GiftTagServiceImpl giftTagService, LinkTableDAOImpl linkTableDAO){
        this.giftCertificate = giftCertificate;
        this.giftTagService = giftTagService;
        this.linkTableDAO = linkTableDAO;
    }

    @GetMapping("/secondPage")
    public Iterable<GiftCertificate> secondPage(){
        return HateoasManager.addLinksToListGiftCertificate(giftCertificate.allGiftCertificate());
    }

    @GetMapping("/{id}")
    public GiftCertificate show(@PathVariable("id") int id) {
       return HateoasManager.addLinksToGiftCertificate(giftCertificate.findGiftById(id));
       // GiftCertificate gift = giftCertificate.findGiftById(id);
       // return gift;
    }

    @PostMapping("/add")
    public Iterable<GiftCertificate> addGift(@ModelAttribute GiftCertificate gift) {
        giftCertificate.addGiftCertificate(gift);
        return giftCertificate.allGiftCertificate();
    }

    @RequestMapping(value = "/addTagToGift", method = RequestMethod.POST)
    public List<GiftTag> addTagToGift(@RequestParam(required = false) String newTag, int idTag){
        giftTagService.addTagToGiftCertificate(newTag, idTag);
        return linkTableDAO.getConcatenatedTables("");
    }

    @DeleteMapping("/{id}")
    public Iterable<GiftCertificate> deleteGift(@PathVariable("id") int id) {
        giftCertificate.deleteGiftCertificate(id);
        return giftCertificate.allGiftCertificate();
    }

    @PatchMapping
    public Iterable<GiftCertificate> updateGift(@ModelAttribute("gift") GiftCertificate gift) {
        giftCertificate.updateGiftCertificate(gift);
        return giftCertificate.allGiftCertificate();
    }

    @PatchMapping("price/{id}")
    public Iterable<GiftCertificate> updateGiftPrice(@ModelAttribute("gift") GiftCertificate gift, @PathVariable("id") int id) {
        giftCertificate.updateGiftCertificatePrice(id, gift.getPrice());
        return giftCertificate.allGiftCertificate();
    }
}