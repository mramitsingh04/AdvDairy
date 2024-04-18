package com.generic.khatabook.product;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class ProductSpecificationApplication {

    public static void main(String[] args) {
        run(ProductSpecificationApplication.class, args);
    }

}
