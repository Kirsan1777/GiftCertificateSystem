package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.UserOrder;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class for implementing dependent references for an object
 */
public class HateoasManager<T> {

    protected Collection<T> addPagination(List<T> entities, int page, int size, long countOfEntities) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, countOfEntities);
        return PagedModel.of(entities, pageMetadata).getContent();
    }

    public static Tag addLinksToTag(Tag tag) {
        tag.add(linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete"));
        return tag;
    }

    public static Iterable<Tag> addLinksToTags(Iterable<Tag> tags) {
        for (Tag tag : tags) {
            tag.add(linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete"));
            tag.add(linkTo(methodOn(TagController.class).show(tag.getId())).withRel("show"));
        }
        return tags;
    }

    public static GiftCertificate addLinksToGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).deleteGift(giftCertificate.getId())).withRel("delete"));
        giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).updateGift(giftCertificate.getId(), giftCertificate)).withRel("update"));
        for (Tag tag : giftCertificate.getTags()) {
            tag.add(linkTo(methodOn(TagController.class).show(tag.getId())).withSelfRel());
        }
        return giftCertificate;
    }

    public static Collection<GiftCertificate> addLinksToListGiftCertificate(Collection<GiftCertificate> giftCertificateList) {
        for (GiftCertificate giftCertificate : giftCertificateList) {
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).deleteGift(giftCertificate.getId())).withRel("delete"));
            giftCertificate.add(linkTo(methodOn(GiftCertificateController.class).updateGift(giftCertificate.getId(), giftCertificate)).withRel("update"));
            for (Tag tag : giftCertificate.getTags()) {
                if (!tag.hasLinks()) {
                    tag.add(linkTo(methodOn(TagController.class).show(tag.getId())).withSelfRel());
                }
            }
        }
        return giftCertificateList;
    }

    public static Iterable<UserOrder> addLinksToListOrder(Iterable<UserOrder> orders) {
        for (UserOrder order : orders) {
            if (!order.hasLinks()) {
                order.add(linkTo(methodOn(GiftCertificateController.class).show(order.getIdCertificate())).withRel("show gift certificate"));
                order.add(linkTo(methodOn(UserController.class).getUserById(order.getIdUser())).withRel("show user"));
            }
        }
        return orders;
    }

    public static UserOrder addLinkToOrder(UserOrder order) {
        order.add(linkTo(methodOn(GiftCertificateController.class).show(order.getIdCertificate())).withRel("show gift certificate"));
        order.add(linkTo(methodOn(UserController.class).getUserById(order.getIdUser())).withRel("show user"));
        return order;
    }

}
