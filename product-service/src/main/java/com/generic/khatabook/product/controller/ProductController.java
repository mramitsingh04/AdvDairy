package com.generic.khatabook.product.controller;

import com.generic.khatabook.product.exceptions.AppEntity;
import com.generic.khatabook.product.exceptions.ApplicationException;
import com.generic.khatabook.product.exceptions.IllegalArgumentException;
import com.generic.khatabook.product.exceptions.InputValidationException;
import com.generic.khatabook.product.exceptions.InvalidArgumentException;
import com.generic.khatabook.product.exceptions.NotFoundException;
import com.generic.khatabook.product.model.ProductDTO;
import com.generic.khatabook.product.model.ProductUpdatable;
import com.generic.khatabook.product.model.ProductViews;
import com.generic.khatabook.product.services.IdGeneratorService;
import com.generic.khatabook.product.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("product-service")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService myProductService;
    private final IdGeneratorService myIdGeneratorService;


    @PostMapping(path = "/products")
    public ResponseEntity<?> createAll(@RequestBody List<ProductDTO> products) {

        if (Objects.nonNull(products) && !products.isEmpty()) {
            products.parallelStream().forEach(this::create);
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> create(@RequestBody ProductDTO product) {
        final List<ProductDTO> entityModel = myProductService.findProductByName(product.name());
        if (isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, product.name()).get()).build();
        }
        try {
            if (isNull(product.id())) {
                product = product.copyOf(myIdGeneratorService.generateId());
            }

            final ProductDTO newProduct = myProductService.saveProduct(product);
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}").buildAndExpand(product.id()).toUri()).body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage())).build();
        }
    }

    @GetMapping("/{productId}")
    @Cacheable(value = "product", key = "#productId")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId)
    {
        final ProductDTO entityModel = myProductService.findProductById(productId).get();
        if (isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/")
    public ResponseEntity<ProductViews> searchProductByQuery(@RequestParam(required = false) String name, @RequestParam(required = false) String unitOfMeasurement)
    {

        final List<ProductDTO> products;
        try {
            if (Objects.nonNull(name)) {
                products = myProductService.findProductByName(name);
            } else if (Objects.nonNull(unitOfMeasurement)) {
                products = myProductService.findProductByUnitOfMeasurement(unitOfMeasurement);
            } else {
                return getAllProducts();
            }
            if (isNull(products)) {
                return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, name).get()).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.of(e.get()).build();
        }
        return ResponseEntity.ok(new ProductViews(products));
    }

    @GetMapping(path = "/products")
    public ResponseEntity<ProductViews> getAllProducts() {

        return ResponseEntity.ok(new ProductViews(myProductService.findAllProducts()));
    }

    @DeleteMapping("/{productId}")
    @CacheEvict(cacheNames = "product", key = "#productId", beforeInvocation = true)
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable String productId) {
        final ProductDTO entityModel = myProductService.findProductById(productId).get();
        if (isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        myProductService.delete(entityModel);
        return ResponseEntity.ok(entityModel);
    }


    @PutMapping("/{productId}")
    @CachePut(cacheNames = "product", key = "#productId")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @RequestBody ProductDTO product) {
        if (!myProductService.findProductById(productId).isPresent()) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }

        final ProductDTO productDTO;
        try {
            productDTO = myProductService.updateProduct(product);
        } catch (InputValidationException e) {
            return ResponseEntity.of(e.get()).build();
        } catch (Exception e) {
            return ResponseEntity.of(new ApplicationException(AppEntity.PRODUCT, e.getMessage()).get()).build();
        }
        return ResponseEntity.ok(productDTO);
    }

    @PatchMapping(path = "/{productId}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updatePartialProduct(@PathVariable String productId, @RequestBody Map<String, Object> productEntities) {
        final ProductUpdatable entityModel = myProductService.findProductById(productId).updatable();
        if (isNull(entityModel)) {
            return ResponseEntity.of(new NotFoundException(AppEntity.PRODUCT, productId).get()).build();
        }
        for (final Map.Entry<String, Object> member : productEntities.entrySet()) {
            final Field field = ReflectionUtils.findField(ProductUpdatable.class, member.getKey());
            if (Objects.nonNull(field)) {
                ReflectionUtils.makeAccessible(field);
                final Object valueToSet = member.getValue();
                if (BigDecimal.class.getName().equals(field.getType().getName())) {
                    ReflectionUtils.setField(field, entityModel, BigDecimal.valueOf((Double) valueToSet));
                } else {
                    ReflectionUtils.setField(field, entityModel, valueToSet);
                }
            } else {
                throw new InvalidArgumentException(AppEntity.PRODUCT, member.getKey());
            }
        }
        final ProductDTO updateProduct = myProductService.updateProduct(entityModel.build());

        return ResponseEntity.ok(updateProduct);
    }
}
