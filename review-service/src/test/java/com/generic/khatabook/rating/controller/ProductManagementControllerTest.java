package com.generic.khatabook.rating.controller;

import com.generic.khatabook.rating.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class ProductManagementControllerTest extends AbstractTest {
    @MockBean
    private RatingService myRatingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateProductsWithNullPayload() throws Exception {
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content("[]")).andExpect(status().isNoContent());
    }
/*

    @Test
    void testRatingExistingProduct() throws Exception {
        String prodId = "1";
        String path = "/product/%s/rating".formatted(prodId);
        RatingDTO productRatingDTO =
                new RatingDTO(null, prodId, prodId, 4.0f, "Good milk product.");

        Container<ProductDTO, ProductUpdatable> prods = getProductDTOS().stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(null);
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        doNothing().when(myProductManagementService).saveProductRating(productRatingDTO);
        mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(productRatingDTO))).andExpect(status().isCreated());
    }

    @Test
    void testRatingNonExistingProduct() throws Exception {
        String prodId = "113";
        String path = "/product/%s/rating".formatted(prodId);
        List<ProductDTO> listOfProducts = getProductDTOS();
        Container<ProductDTO, ProductUpdatable> prods =
                listOfProducts.stream().filter(x -> x.id().equals(prodId)).map(x -> new Container(x, x.updatable())).findFirst().orElse(Container.empty());
        when(myProductManagementService.findProductById(prodId)).thenReturn(prods);
        ProductRatingDTO productRatingDTO =
                new ProductRatingDTO(null, prodId, prodId, 4.0f, "Good milk product.");
        mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content(mapToJson(productRatingDTO))).andExpect(status().isNotFound());
    }
*/

}