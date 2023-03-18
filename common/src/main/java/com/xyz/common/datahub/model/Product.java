package com.xyz.common.datahub.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Data
@Document("Product")
public class Product {
    @Id
    private String id;
    private String productId;
    private String name;
    private int quantity;
    @Field(targetType = DECIMAL128)
    private BigDecimal price;
    public Date createdDate;
    public Date lastUpdate;
}
