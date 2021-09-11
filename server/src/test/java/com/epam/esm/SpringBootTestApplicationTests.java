package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpringBootTestApplicationTests {

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private static GiftCertificateServiceImpl giftCertificateService;
    private static GiftCertificate giftCertificate = new GiftCertificate(1,"newTag",200,1,
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0,0),
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0,0),
            "newGift", null);
    private static GiftCertificate giftCertificateUpdated = new GiftCertificate(1,"newTagUpdate",200,1,
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0,0),
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0,0),
            "newGift", null);
    private GiftCertificateDto giftDto = modelMapper().map(giftCertificate, GiftCertificateDto.class);

    @BeforeAll
    public static void setup(){
        GiftCertificateDAO giftCertDAO = mock(GiftCertificateDAO.class);
        when(giftCertDAO.save(giftCertificate)).thenReturn(giftCertificate);
        when(giftCertDAO.save(giftCertificateUpdated)).thenReturn(giftCertificate);
        when(giftCertDAO.findAll()).thenReturn(new ArrayList<GiftCertificate>(Arrays.asList(giftCertificateUpdated)));
        giftCertificateService = new GiftCertificateServiceImpl(giftCertDAO);
    }

    @Test
    public void checkModelMapperResult(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(100);
        giftCertificate.setId(1);
        GiftCertificateDto giftDto = modelMapper().map(giftCertificate, GiftCertificateDto.class);
        assertEquals(giftDto.getId(), giftCertificate.getId());
    }


}
