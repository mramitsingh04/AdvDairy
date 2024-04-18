package com.tester.cache.api;

import com.tester.cache.entity.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class EntityController {

    @GetMapping("/product/{id}")
    @Cacheable(value = "product", key = "#id")
    public Product getProductById(@PathVariable long id) {

        return null;
    }

    @PutMapping("/product/{id}")
    @CachePut(cacheNames = "product", key = "#id")
    public Product editProduct(@PathVariable long id, @RequestBody Product product) {
        return null;

    }

    @DeleteMapping("/product/{id}")
    @CacheEvict(cacheNames = "product", key = "#id", beforeInvocation = true)
    public String removeProductById(@PathVariable long id) {

        return null;
    }
}
