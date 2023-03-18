package com.xyz.common.datahub.repository;

import com.xyz.common.datahub.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findProductByProductId(String productId);
}
