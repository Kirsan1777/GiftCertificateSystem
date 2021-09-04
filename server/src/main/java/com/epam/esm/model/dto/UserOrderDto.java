package com.epam.esm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto extends RepresentationModel<UserOrderDto> {
    private int idUser;
    private int idCertificate;
    private double cost;
    private LocalDateTime timeOfPurchase;
}
