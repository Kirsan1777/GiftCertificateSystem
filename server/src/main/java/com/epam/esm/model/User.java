package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double balance;
    private String name;

    public User() {
    }

    public User(int id, double balance, String name) {
        this.id = id;
        this.balance = balance;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Double.compare(user.balance, balance) == 0 && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
