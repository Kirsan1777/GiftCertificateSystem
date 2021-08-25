package com.epam.esm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private double balance;
    @Size(min = 1, max = 100, message = "username length should contain from 1 to 100 symbols")
    private String username;
    @Size(min = 4, max = 50, message = "password length should contain from 4 to 50 symbols")
    private String password;
    private String operationStatus;
}
