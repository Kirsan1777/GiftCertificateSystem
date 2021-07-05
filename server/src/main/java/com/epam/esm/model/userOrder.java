package com.epam.esm.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class userOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int idUser;
    private int idCertificate;
    private double cost;
    private LocalDateTime timeOfPurchase;

    public userOrder() {
    }

    public userOrder(int id, int idUser, int idCertificate, double cost, LocalDateTime timeOfPurchase) {
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
