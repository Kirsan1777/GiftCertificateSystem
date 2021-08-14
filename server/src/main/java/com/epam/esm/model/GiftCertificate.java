package com.epam.esm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * The class of gift certificate
 */
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @Column(name = "price")
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "many_to_many",
            joinColumns = {@JoinColumn(name = "id_certificate")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag")}
    )
    private List<Tag> tags;

    public GiftCertificate(){
    }

    public GiftCertificate(int id, String description, double price, int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, String name, List<Tag> tags) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.name = name;
        this.tags = tags;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id && Double.compare(that.price, price) == 0 && duration == that.duration && Objects.equals(description, that.description) && Objects.equals(createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(name, that.name) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, description, price, duration, createDate, lastUpdateDate, name, tags);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                '}';
    }
}
