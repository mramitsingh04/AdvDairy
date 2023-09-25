package com.generic.khatabook.factory;

import com.generic.khatabook.model.Product;
import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.model.UnitOfMeasurement;
import com.generic.khatabook.entity.CustomerProduct;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class ProductFactory {

    public static final String SHOW_ROOM = "ShowRoom";
    public static final String METER = "Meter";
    public static final String ROOM = "room";
    public static final String MILK = "milk";
    public static final String SUGAR = "sugar";
    static Map<String, Product> productsViews = new HashMap<>();
    static Map<String, ProductDTO> productDTO = new HashMap<>();

    static {
        productsViews.put(MILK, getMilkProductView());
        productsViews.put(SUGAR, getSugarProductView());
        productsViews.put(ROOM, getRoomProductView());
        productsViews.put(SHOW_ROOM, getShowRoomProductView());
        productsViews.put(METER, getMeterProductView());


        productDTO.put(getMilkProduct().id(), getMilkProduct());
        productDTO.put(getSugarProduct().id(), getSugarProduct());
        productDTO.put(getRoomProduct().id(), getRoomProduct());
        productDTO.put(getShowRoomProduct().id(), getShowRoomProduct());
        productDTO.put(getMeterProduct().id(), getMeterProduct());

    }

    private static Product getMeterProductView() {
        return new Product(METER, METER);
    }

    private static Product getShowRoomProductView() {
        return new Product(SHOW_ROOM, SHOW_ROOM);
    }

    private static ProductDTO getMeterProduct() {
        return getProduct(METER, 1, BigDecimal.valueOf(9), UnitOfMeasurement.READING);
    }

    public static ProductDTO getProduct(String idAndName, int quantity, BigDecimal price, UnitOfMeasurement unitOfMeasurement) {
        return new ProductDTO(idAndName, idAndName, quantity, price, unitOfMeasurement, 0f);
    }

    private static ProductDTO getShowRoomProduct() {
        return getProduct(SHOW_ROOM, 1, BigDecimal.valueOf(50000), UnitOfMeasurement.ITEM);
    }

    private static ProductDTO getRoomProduct() {
        return getProduct(ROOM, 1, BigDecimal.valueOf(5000), UnitOfMeasurement.ITEM);
    }

    public static ProductDTO getMilkProduct() {
        return getProduct(MILK, 1, BigDecimal.valueOf(50), UnitOfMeasurement.LITTER);
    }

    public static ProductDTO getSugarProduct() {
        return getProduct(SUGAR, 1, BigDecimal.TEN.multiply(BigDecimal.valueOf(4)), UnitOfMeasurement.KILOGRAM);
    }

    public static Product getSugarProductView() {
        return new Product(SUGAR, SUGAR);
    }

    public static Product getMilkProductView() {
        return new Product(MILK, MILK);
    }

    public static Product getRoomProductView() {
        return new Product(ROOM, ROOM);
    }

    public static List<Product> getProductsView(String... ids) {

        if (isNull(ids)) {
            return Collections.emptyList();
        }
        return Arrays.stream(ids).map(productsViews::get).collect(Collectors.toList());
    }

    public static List<ProductDTO> getProducts(String... ids) {

        if (isNull(ids)) {
            return Collections.emptyList();
        }
        return Arrays.stream(ids).map(productDTO::get).collect(Collectors.toList());
    }

    public static List<Product> getAllProductView() {

        return productsViews.entrySet().stream().map(productsViews::get).collect(Collectors.toList());
    }

    public static List<CustomerProduct> getCustomerProductsEntity(String... ids) {

        if (isNull(ids)) {
            return Collections.emptyList();
        }

        return Arrays.stream(ids).sequential().map(productsViews::get).map(Product::productId).map(ProductFactory::buildCustomerProductEntity).collect(Collectors.toList());
    }

    private static CustomerProduct buildCustomerProductEntity(String productId) {
        return CustomerProduct.builder().productId(productId).productName(productId).build();
    }
}
