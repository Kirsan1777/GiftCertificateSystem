package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The class of user orders
 */
@Entity
@Table(name = "user_order")
public class UserOrder extends RepresentationModel<UserOrder> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "id_user")
    private int idUser;
    @Column(columnDefinition = "id_certificate")
    private int idCertificate;
    @Column(columnDefinition = "cost")
    private double cost;
    @Column(columnDefinition = "time_of_purchase")
    private LocalDateTime timeOfPurchase;

    public UserOrder() {
    }

    public UserOrder(int id, int idUser, int idCertificate, double cost, LocalDateTime timeOfPurchase) {
        this.id = id;
        this.idUser = idUser;
        this.idCertificate = idCertificate;
        this.cost = cost;
        this.timeOfPurchase = timeOfPurchase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCertificate() {
        return idCertificate;
    }

    public void setIdCertificate(int idCertificate) {
        this.idCertificate = idCertificate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDateTime getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public void setTimeOfPurchase(LocalDateTime timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }
}
