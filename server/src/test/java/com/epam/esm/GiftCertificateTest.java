package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateTest {

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Mock
    private static GiftCertificateServiceImpl giftCertificateService;
    private static GiftCertificate giftCertificate = new GiftCertificate(1, "newTag", 200, 1,
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            "newGift", null);
    private static GiftCertificate giftCertificateUpdated = new GiftCertificate(1, "newTagUpdate", 200, 1,
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            "newGift", null);
    private GiftCertificateDto giftDto = modelMapper().map(giftCertificate, GiftCertificateDto.class);

    @BeforeAll
    public static void setup() {
        GiftCertificateDAO giftCertDAO = mock(GiftCertificateDAO.class);
        when(giftCertDAO.save(giftCertificate)).thenReturn(giftCertificate);
        when(giftCertDAO.save(giftCertificateUpdated)).thenReturn(giftCertificate);
        when(giftCertDAO.findAll()).thenReturn(new ArrayList<GiftCertificate>(Arrays.asList(giftCertificateUpdated)));
        giftCertificateService = new GiftCertificateServiceImpl(giftCertDAO);
    }

    @Test
    public void addGiftTestValid(){
        GiftCertificateDto gift = giftDto;
        when(giftCertificateService.addGiftCertificateWithResult(any(GiftCertificateDto.class))).thenReturn(giftCertificate);
        assertEquals(giftCertificate, giftCertificateService.addGiftCertificateWithResult(gift));
    }

    @Test
    public void findOneGiftCertificateTest(){
        GiftCertificateDto gift = giftDto;
        when(giftCertificateService.findById(1)).thenReturn(gift);
        assertEquals(gift, giftCertificateService.findById(1));
    }

    @Test
    public void filterGiftCertificateTest(){
        List<String> tags =new ArrayList<>();
        tags.add("we");
        tags.add("are");
        tags.add("number");
        tags.add("one");
        List<GiftCertificateDto> giftsDto = new ArrayList<>();
        GiftCertificateDto gift = giftDto;
        giftsDto.add(gift);
        when(giftCertificateService.filter(tags)).thenReturn(giftsDto);
        assertEquals(giftsDto, giftCertificateService.filter(tags));
    }


}