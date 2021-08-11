package com.epam.esm.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Objects;

/**
 * The class of gift certificate with tag
 */
@Entity
public class GiftTag extends RepresentationModel<GiftTag> {
    //use many to many annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idTag;
    private String tagName;
    private String giftName;
    private double price;
    private int duration;
    private String description;
    private String createDate;
    private String lastUpdateDate;

    public GiftTag() {
    }

    public GiftTag(int id, int idTag, String tagName, String giftName, double price, int duration, String description, String createDate, String lastUpdateDate) {
        this.id = id;
        this.idTag = idTag;
        this.tagName = tagName;
        this.giftName = giftName;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftTag giftTag = (GiftTag) o;
        return Double.compare(giftTag.price, price) == 0 && duration == giftTag.duration && Objects.equals(tagName, giftTag.tagName) && Objects.equals(giftName, giftTag.giftName) && Objects.equals(description, giftTag.description) && Objects.equals(createDate, giftTag.createDate) && Objects.equals(lastUpdateDate, giftTag.lastUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, giftName, price, duration, description, createDate, lastUpdateDate);
    }

    @Override
    public String toString() {
        return "GiftTag{" +
                "tagName='" + tagName + '\'' +
                ", giftName='" + giftName + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", description='" + description + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                '}';
    }
}
