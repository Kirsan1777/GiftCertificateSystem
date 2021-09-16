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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateTest {

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Mock
    private static GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private static GiftCertificateDAO giftDao;

    private static GiftCertificate giftCertificate = new GiftCertificate(1, "newTag", 200, 1,
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            LocalDateTime.of(2017, Month.NOVEMBER, 30, 0, 0),
            "newGift", null);
    private GiftCertificateDto giftDto = modelMapper().map(giftCertificate, GiftCertificateDto.class);

    @Test
    void addGiftCertificate(){
        giftCertificateService.addGiftCertificate(giftDto);
        verify(giftCertificateService, times(1)).addGiftCertificate(any(GiftCertificateDto.class));
    }

    @Test
    void addGiftCertificateWithResult(){
        GiftCertificateDto gift = giftDto;
        when(giftCertificateService.addGiftCertificateWithResult(any(GiftCertificateDto.class))).thenReturn(giftCertificate);
        assertEquals(giftCertificate, giftCertificateService.addGiftCertificateWithResult(gift));
    }

    @Test
    void findById(){
        GiftCertificateDto gift = giftDto;
        when(giftCertificateService.findById(1)).thenReturn(gift);
        assertEquals(gift, giftCertificateService.findById(1));
    }

    @Test
    void deleteGiftCertificate(){
        giftCertificateService.deleteGiftCertificate(1);
        verify(giftCertificateService, times(1)).deleteGiftCertificate(any(int.class));
    }

    @Test
    void updateGiftCertificate(){
        GiftCertificateDto gift = giftDto;
        giftCertificateService.updateGiftCertificate(gift);
        verify(giftCertificateService, times(1)).updateGiftCertificate(any(GiftCertificateDto.class));
    }

    @Test
    void updateGiftCertificatePrice(){
        GiftCertificateDto gift = giftDto;
        giftCertificateService.updateGiftCertificatePrice(gift.getId(), gift.getPrice());
        verify(giftCertificateService, times(1)).updateGiftCertificatePrice(any(int.class), any(double.class));
    }

    @Test
    void filterTest(){
        List<String> tags =new ArrayList<>();
        tags.add("we");
        tags.add("are");
        tags.add("number");
        tags.add("one");
        giftCertificateService.filter(tags);
        verify(giftCertificateService, times(1)).filter(any(List.class));
    }

    @Test
    void filterGiftCertificateTest(){
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