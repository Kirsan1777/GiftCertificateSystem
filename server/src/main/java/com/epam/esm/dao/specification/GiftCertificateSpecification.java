package com.epam.esm.dao.specification;

import com.epam.esm.dao.type.SearchType;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Optional;

public class GiftCertificateSpecification {



    public static Specification<GiftCertificate> filterCertificatesByTag(String tagName) {
        return (root, query, builder) -> {
            Join<GiftCertificate, Tag> certificateTagJoin = root.join("tags");
            return builder.isTrue(certificateTagJoin.get("name").in(tagName));
        };
    }

}
