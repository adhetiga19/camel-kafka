package com.xyz.product.route;

import com.xyz.common.datahub.model.Product;
import com.xyz.common.datahub.repository.ProductRepository;
import com.xyz.common.model.ProductListReq;
import com.xyz.common.model.ProductReq;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProductRouter extends RouteBuilder {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void configure() throws Exception {
        from("kafka:{{product_upload_topic}}")
                .unmarshal().json(JsonLibrary.Jackson, ProductListReq.class)
                .process(exchange -> {
                    ProductListReq productReq = exchange.getMessage().getBody(ProductListReq.class);
                    List<ProductReq> productReqList = productReq.getProductListList();

                    List<Product> products = new ArrayList<>();
                    productReqList.forEach(e -> {
                        Product product = new Product();
                        product.setProductId(e.getId());
                        product.setName(e.getName());
                        product.setQuantity(e.getQuantity());
                        product.setPrice(new BigDecimal(e.getPrice()));
                        product.setCreatedDate(new Date());

                        products.add(product);
                    });

                    System.out.println("Saving data...");
                    productRepository.saveAll(products);
                });

        from("kafka:{{product_delete_topic}}")
                .process(exchange -> {
                    String productId = exchange.getMessage().getHeader("productId", String.class);

                    System.out.println("Find product by productId...");
                    Product product = productRepository.findProductByProductId(productId);

                    System.out.println("Deleting data...");
                    productRepository.delete(product);
                });

        from("kafka:{{product_update_topic}}")
                .unmarshal().json(JsonLibrary.Jackson, ProductReq.class)
                .process(exchange -> {
                    ProductReq productReq = exchange.getMessage().getBody(ProductReq.class);
                    String productId = exchange.getMessage().getHeader("productId", String.class);

                    System.out.println("Find product by productId...");
                    Product product = productRepository.findProductByProductId(productId);

                    if(product != null){
                        product.setProductId(productReq.getId());
                        product.setName(productReq.getName());
                        product.setQuantity(productReq.getQuantity());
                        product.setPrice(new BigDecimal(productReq.getPrice()));
                        product.setLastUpdate(new Date());

                        System.out.println("Updating data...");
                        productRepository.save(product);
                    }
                });
    }
}
