package com.epam.esm.dao.query;

public class SqlManyToManyQuery {
    public static final String ADD_A_GIFT_TAG = "INSERT many_to_many(id_tag, id_certificate) VALUES (?, ?)";
    public static final String SELECT_ALL_PARAM = "SELECT t.name, c.name, c.price, c.duration, c.description, c.create_date, c.last_update_date" +
            " FROM many_to_many gct JOIN tag t ON gct.id_tag = t.id JOIN gift_certificate c ON gct.id_certificates = c.id;";

}
