package com.epam.esm.dao.query;

public class SqlGiftCertificateQuery {
        public static final String ADD_CERTIFICATE = "INSERT INTO gift_certificate(description, price, duration, " +
                "create_date, last_update_date, name) VALUES (?, ?, ?, ?, ?, ?)";
        public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
        public static final String SELECT_ALL_CERTIFICATE = "SELECT * FROM gift_certificate";
        public static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET description = ?, " +
                "price = ?, duration = ?, create_date = ?, last_update_date = ?, name = ? WHERE id = ?";
        public static final String UPDATE_PRICE_CERTIFICATE = "UPDATE gift_certificate SET " +
                "price = ?, last_update_date = ? WHERE id = ?";
        public static final String SELECT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
}
