package com.xyz.api;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ProductApi extends RouteBuilder {
    @Override
    public void configure() {

        rest("/product")
                .post("/upload")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .to("kafka:{{product_upload_topic}}");

        rest("/product")
                .delete("/delete")
                .param().name("productId").type(RestParamType.query).required(true).endParam()
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .to("kafka:{{product_delete_topic}}");

        rest("/product")
                .patch("/update")
                .param().name("productId").type(RestParamType.query).required(true).endParam()
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .to("kafka:{{product_update_topic}}");
    }
}
