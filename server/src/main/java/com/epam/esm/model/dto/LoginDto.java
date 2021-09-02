package com.epam.esm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    //@Min(value = 4, message = "Input name more than 3 symbols")
    //@Max(value = 50, message = "Max name length can be 50 symbols")
    private String username;
    @NotNull
    //@Min(value = 4, message = "Input password more than 3 symbols")
    //@Max(value = 50, message = "Max password length can be 250 symbols")
    private String password;
}
