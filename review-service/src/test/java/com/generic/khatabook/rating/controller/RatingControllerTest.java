package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.exchanger.ProductClient;
import com.generic.khatabook.rating.model.ProductDTO;
import com.generic.khatabook.rating.model.RatingDTO;
import com.generic.khatabook.rating.model.UnitOfMeasurement;
import com.generic.khatabook.rating.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.generic.khatabook.rating.model.RatingEntityType.PRODUCT;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest extends AbstractTest {
    @MockBean
    private RatingService myRatingService;

    @MockBean
    private ProductClient myProductClient;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateProductsWithNullPayload() throws Exception {
        mockMvc.perform(post("/rating-service/").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isNoContent());
    }

    @Test
    void testExistRatingForEntityTypeProduct() throws Exception {
        String entityId = "entityId-01";
        final String fromCustomerId = "customerId-01";
        RatingDTO ratingRequest =
                new RatingDTO(null, fromCustomerId, PRODUCT, entityId, 4.0f, "Good milk product.");

        final RatingDTO ratingResponse =
                getReviews().stream().filter(x -> x.entityType() == ratingRequest.entityType()).filter(x -> x.customerId().equals(ratingRequest.customerId()))
                        .filter(x -> x.entityId().equals(ratingRequest.entityId()))
                        .findFirst().orElse(null);
        ProductDTO productEntityId =
                getProductDTOS().stream().filter(x -> x.id().equals(ratingRequest.entityId())).findFirst().orElse(null);
        when(myProductClient.getProductById(anyString())).thenReturn(ResponseEntity.ok(productEntityId));


        when(myRatingService.updateRating(productEntityId, ratingRequest)).thenReturn(ratingResponse);

        mockMvc.perform(post("/rating-service/").contentType(MediaType.APPLICATION_JSON).content(mapToJson(ratingRequest))).andExpect(status().isCreated());
    }

    private static List<RatingDTO> getReviews() {
        List<RatingDTO> listOfProducts = new ArrayList<>();
        listOfProducts.add(new RatingDTO("1", "customerId-01", PRODUCT, "entityId-01", 3.0f, "Good milk product."));
        listOfProducts.add(new RatingDTO("2", "customerId-01", PRODUCT, "entityId-02", 4.0f, ""));
        listOfProducts.add(new RatingDTO("6", "customerId-02", PRODUCT, "entityId-02", 4.0f, ""));
        listOfProducts.add(new RatingDTO("3", "customerId-03", PRODUCT, "entityId-03", 2.0f, ""));
        listOfProducts.add(new RatingDTO("4", "customerId-04", PRODUCT, "entityId-04", 5.0f, ""));
        listOfProducts.add(new RatingDTO("5", "customerId-05", PRODUCT, "entityId-05", 1.0f, ""));
        return listOfProducts;
    }

    private static List<ProductDTO> getProductDTOS() {
        List<ProductDTO> listOfProducts = new ArrayList<>();
        listOfProducts.add(new ProductDTO("entityId-01", "Milk", 1, BigDecimal.TEN, UnitOfMeasurement.LITTER, 3.0f));
        listOfProducts.add(new ProductDTO("entityId-02", "Unit", 1, BigDecimal.TEN, UnitOfMeasurement.READING, 4.0f));
        listOfProducts.add(new ProductDTO("entityId-03", "Room", 1, BigDecimal.TEN, UnitOfMeasurement.ITEM, 2.0f));
        listOfProducts.add(new ProductDTO("entityId-04", "Cash", 1, BigDecimal.TEN, UnitOfMeasurement.ITEM, 5.0f));
        listOfProducts.add(new ProductDTO("entityId-05", "ShowRoom", 1, BigDecimal.TEN, UnitOfMeasurement.ITEM, 1.0f));
        return listOfProducts;
    }

    @Test
    void testRatingNonExistingProduct() throws Exception {

    }

}