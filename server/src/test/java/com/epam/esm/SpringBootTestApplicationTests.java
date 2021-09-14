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

    @Test
    public void checkModelMapperResult(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(100);
        giftCertificate.setId(1);
        GiftCertificateDto giftDto = modelMapper().map(giftCertificate, GiftCertificateDto.class);
        assertEquals(giftDto.getId(), giftCertificate.getId());
    }




}
