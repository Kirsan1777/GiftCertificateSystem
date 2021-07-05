package com.epam.esm.controller;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("message")
@EnableAutoConfiguration
public class Controller {
    private GiftCertificateServiceImpl giftCertificateService;

    public Controller() {
    }

    @Autowired
    public Controller(GiftCertificateServiceImpl giftCertificateService){
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public Iterable<GiftCertificate>  list(){
        return giftCertificateService.allGiftCertificate("");
    }

    //session factory hibernate

}
