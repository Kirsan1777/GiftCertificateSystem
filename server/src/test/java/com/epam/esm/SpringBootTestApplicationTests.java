package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.model.*;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.LoginDto;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringBootTestApplicationTests {

    @Mock
    private static TagDAO tagDAO;
    @Mock
    private AuthenticationManager authenticationManager;
    private GiftTagService giftTagService;
    @Mock
    private UserServiceImpl userService;
    private final SecurityServiceImpl securityService = new SecurityServiceImpl();


    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @BeforeEach
    void init(){
        giftTagService = new GiftTagServiceImpl(tagDAO);
        securityService.setAuthenticationManager(authenticationManager);
        securityService.setUserService(userService);
    }

    @Test
    void testCheckModelMapperResult(){
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setPrice(100);
        giftCertificate.setId(1);
        GiftCertificateDto giftDto = modelMapper().map(giftCertificate, GiftCertificateDto.class);
        assertEquals(giftDto.getId(), giftCertificate.getId());
    }

    @Test
    void testAddTagToGiftCertificateWithoutAddTag(){
        Tag tag = new Tag("Tag");
        when(tagDAO.findTagByName(any(String.class))).thenReturn(tag);
        giftTagService.addTagToGiftCertificate("Tag", 1);
        verify(tagDAO, times(1)).addTagToGift(0, 1);
    }

    @Test
    void testAddTagToGiftCertificate(){
        Tag tag = new Tag("Tag");
        when(tagDAO.findTagByName(any(String.class))).thenReturn(null).thenReturn(tag);
        when(tagDAO.save(any(Tag.class))).thenReturn(tag);
        giftTagService.addTagToGiftCertificate("Tag", 1);
        verify(tagDAO, times(1)).save(tag);
    }

    @Test
    void testLoadUserByUsername(){
        LoginDto loginDto = new LoginDto("User", "user");
        User user = new User(1, 1, "User", "User", Status.ACTIVE, UserRole.ADMIN);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.loadUserByUsername(any(String.class))).thenReturn(user);
        assertEquals(user, securityService.loginUser(loginDto));
    }

}
