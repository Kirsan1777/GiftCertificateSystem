package com.epam.esm.service.impl;

import com.epam.esm.model.User;
import com.epam.esm.model.dto.LoginDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class SecurityServiceImpl {
    private AuthenticationManager authenticationManager;
    private UserServiceImpl userService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public User loginUser(LoginDto loginDto) {
        try {
            String username = loginDto.getUsername();
            String password = loginDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.loadUserByUsername(username);
            if (isNull(user)) {
                throw new UsernameNotFoundException("user not found");
            }
            return user;
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
