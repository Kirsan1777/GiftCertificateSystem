package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Test extends JpaRepository<GiftCertificate, Integer> {
}
