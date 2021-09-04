package com.epam.esm.dao.specification;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

/**
 * The class for create specification request
 */
public class GiftCertificateSpecification {

    public static Specification<GiftCertificate> filterCertificatesByTag(String tagName) {
        return (root, query, builder) -> {
            Join<GiftCertificate, Tag> certificateTagJoin = root.join("tags");
            return builder.isTrue(certificateTagJoin.get("name").in(tagName));
        };
    }

}
