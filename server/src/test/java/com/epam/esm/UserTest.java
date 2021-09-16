package com.epam.esm;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.model.Status;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.UserRole;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private UserService userService;
    @Mock
    private UserDAO userDAO;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private GiftCertificateServiceImpl giftCertificateService;

    User user = new User(1,1, "user", "user", Status.ACTIVE, UserRole.ADMIN);
    UserDto userDto = modelMapper().map(user, UserDto.class);



    @BeforeEach
    void init(){
        userService = new UserServiceImpl(userDAO, orderDAO, tagDAO, giftCertificateService, modelMapper(), passwordEncoder);
    }

    @Test
    void createUserFalseTest(){
        when(userDAO.findByUsername(any(String.class))).thenReturn(user);
        String checkLine = "Username is taken";
        assertEquals(checkLine, userService.createUser(userDto));
    }

    @Test
    void createUserTrueTest(){
        when(userDAO.findByUsername(any(String.class))).thenReturn(null);
        when(userDAO.save(any(User.class))).thenReturn(user);
        String checkLine = "User was created";
        assertEquals(checkLine, userService.createUser(userDto));
    }

    @Test
    void getUserByIdTest(){
        Optional<User> optionalUser = Optional.of(user);
        when(userDAO.findById(1)).thenReturn(optionalUser);
        assertEquals(userDto, userService.getUserById(1));
    }

    @Test
    void findMostUsedUserTagTest(){
        Tag tag = new Tag("Kek");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        when(tagDAO.findMostUsedUserTag(1)).thenReturn(tags);
        assertEquals(tags, userService.findMostUsedUserTag(1));
    }

    @Test
    void loadUserByUsername(){
        when(userDAO.findByUsername(any(String.class))).thenReturn(user);
        assertEquals(user, userService.loadUserByUsername("User"));
    }

}
