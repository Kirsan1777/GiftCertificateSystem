package com.epam.esm.service;

import com.epam.esm.model.User;
import com.epam.esm.model.dto.LoginDto;

public interface SecurityService {

    /**
     * Method for authenticate user
     *
     * @param loginDto class with information about user, login and password
     */
    User loginUser(LoginDto loginDto);
}
