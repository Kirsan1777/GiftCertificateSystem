package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.GiftTagController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.*;
import com.epam.esm.service.impl.TagServiceImpl;
import org.hibernate.criterion.Order;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.core.LinkBuilderSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class HateoasManager<T> {
/*
    linkTo() – responsible for creating the link
    methodOn() – enables to dynamically generate the path to a given resource. We don’t need to
    hardcode the path, but we can refer to the method in the controller.
*/
    protected Collection<T> addPagination(List<T> entities, int page, int size, long countOfEntities) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, countOfEntities);
        return PagedModel.of(entities, pageMetadata).getContent();
    }

    public static Tag addLinksToTag(Tag tag){
        tag.add(linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete"));
        return tag;
    }

    public static Iterable<Tag> addLinksToTags(Iterable<Tag> tags){
        for (Tag tag : tags) {
            tag.add(linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete"));
            tag.add(linkTo(methodOn(TagController.class).show(tag.getId())).withRel("show"));
        }
        return tags;
    }

    public static GiftCertificate addLinksToGiftCertificate(GiftCertificate giftCertificate){
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).deleteGift(giftCertificate.getId())).withRel("delete"));
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).updateGift(giftCertificate)).withRel("update"));
        for(Tag tag : giftCertificate.getTags()){
                tag.add(linkTo(methodOn(TagController.class).show(tag.getId())).withSelfRel());
        }
        return giftCertificate;
    }

    public static Collection<GiftCertificate> addLinksToListGiftCertificate(Collection<GiftCertificate> giftCertificateList){
        for(GiftCertificate giftCertificate : giftCertificateList) {
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).deleteGift(giftCertificate.getId())).withRel("delete"));
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).updateGift(giftCertificate)).withRel("update"));
                for (Tag tag : giftCertificate.getTags()) {
                    if (!tag.hasLinks()) {
                        tag.add(linkTo(methodOn(TagController.class).show(tag.getId())).withSelfRel());
                    }
                }
        }
        return giftCertificateList;
    }

    public static void addLinksToListUsers(List<User> users){
    }

    public static void addLinksToUser(User user){

    }

    public static void addLinksToListOrder(List<UserOrder> orders){

    }

    public static void addLinkToOrder(Order order){

    }

    /*public static UserOrder addLinksToOrder(UserOrder order) {
        addLinksToListCertificate(orderDto.getGiftCertificateList());
        User user = order.getUser();
        user.add(linkTo(methodOn(UserController.class).read(user.getId())).withRel("user"));
        return orderDto;
    }

    public static void addLinksToListCertificate(List<GiftCertificate> certificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : certificateDtoList) {
            giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class).read(giftCertificateDto.getId())).withSelfRel());
            addLinksToListTag(giftCertificateDto.getTags());
        }
    }*/

    /*public static GiftTag addLinksToGiftTag(GiftTag giftTag){
        giftTag.add(linkTo(methodOn(GiftTagController.class).(giftCertificate.getId())).withRel("delete"));
    }*/
}
