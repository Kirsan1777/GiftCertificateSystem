package com.epam.esm.model.dto;

import com.epam.esm.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private double balance;
    private String username;
    private String password;
}
