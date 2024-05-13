package com.generic.khatabook.controller;

import com.generic.khatabook.factory.ProductFactory;
import com.generic.khatabook.model.*;
import com.generic.khatabook.service.*;
import com.generic.khatabook.validator.CustomerValidation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest extends AbstractTest {

    @MockBean
    private CustomerService myCustomerService;

    @MockBean
    private KhatabookRestService myKhatabookService;

    @MockBean
    private PaymentService myPaymentService;

    @MockBean
    private CustomerValidation myCustomerValidation;

    @MockBean
    private CustomerSpecificationService customerSpecificationService;

    @Autowired
    private MockMvc mockMvc;
    private KhatabookView mockKhatabook = new KhatabookView(GroupView.of("123"), "bookId", "khatabookId-01", null, null, null);
    private KhatabookDTO mockKhatabookdto = new KhatabookDTO("123", "bookId", "khatabookId-01", null, null, null);

    @Test
    void testGetCustomersForNonExistingKhatabook() throws Exception {
        String index = "01";
        String path = "/khatabook/khatabookId-" + index + "/customers";
        when(myKhatabookService.getKhatabookByKhatabookId(anyString())).thenReturn(null);
        MockHttpServletResponse response = mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(404, response.getStatus());

        String contentAsString = response.getContentAsString();
        ProblemDetail problemDetail = mapFromJson(contentAsString, ProblemDetail.class);
        assertNotNull(problemDetail);
        assertEquals(404, problemDetail.getStatus());
        assertEquals("Resource not found.", problemDetail.getTitle());
        assertEquals("Khatabook khatabookId-01 not fount!.", problemDetail.getDetail());
        assertNotNull(problemDetail.getInstance());
        assertEquals(path, problemDetail.getInstance().getPath());


    }

    @Test
    void testGetCustomerByCustomerIdWhenKhatabookNotExist() throws Exception {
        String index = "01";
        String path = "/khatabook/khatabookId-%s/customer/customerId-%s".formatted(index, index);
        when(myKhatabookService.getKhatabookByKhatabookId(anyString())).thenReturn(null);
        MockHttpServletResponse response = mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(404, response.getStatus());

        String contentAsString = response.getContentAsString();
        ProblemDetail problemDetail = mapFromJson(contentAsString, ProblemDetail.class);
        assertNotNull(problemDetail);
        assertEquals(404, problemDetail.getStatus());
        assertEquals("Resource not found.", problemDetail.getTitle());
        assertEquals("Khatabook khatabookId-01 not fount!.", problemDetail.getDetail());
        assertNotNull(problemDetail.getInstance());
        assertEquals(path, problemDetail.getInstance().getPath());


    }

    @Test
    void testGetCustomerByCustomerIdWhenKhatabookExistCustomerNotExist() throws Exception {
        String index = "01";
        String path = "/khatabook/khatabookId-%s/customer/customerId-%s".formatted(index, index);
        when(myKhatabookService.getKhatabookByKhatabookId(anyString())).thenReturn(mockKhatabook);
        Container<CustomerDTO, CustomerUpdatable> mockCustomer = Container.empty();
        when(myCustomerService.getByCustomerId(anyString())).thenReturn(mockCustomer);
        MockHttpServletResponse response = mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(404, response.getStatus());

        String contentAsString = response.getContentAsString();
        ProblemDetail problemDetail = mapFromJson(contentAsString, ProblemDetail.class);
        assertNotNull(problemDetail);
        assertEquals(404, problemDetail.getStatus());
        assertEquals("Resource not found.", problemDetail.getTitle());
        assertEquals("Customer customerId-01 not fount!.", problemDetail.getDetail());
        assertNotNull(problemDetail.getInstance());
        assertEquals(path, problemDetail.getInstance().getPath());
    }

    @Test
    void createCustomerWhenKhatabookNotFound() throws Exception {

        String index = "01";
        String path = "/khatabook/khatabookId-%s/customer".formatted(index);
        CustomerDTO customerDTO = getCustomerDTO(index);
        String newCustomerJson = mapToJson(customerDTO);
        MockHttpServletResponse response = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerJson)).andReturn().getResponse();
        assertEquals(404, response.getStatus());

        String contentAsString = response.getContentAsString();
        ProblemDetail problemDetail = mapFromJson(contentAsString, ProblemDetail.class);
        assertNotNull(problemDetail);
        assertEquals(404, problemDetail.getStatus());
    }

    private static CustomerDTO getCustomerDTO(String index) {
        List<Product> products = new ArrayList<>();
        products.add(ProductFactory.getMilkProductView());
        products.add(ProductFactory.getSugarProductView());
        return new CustomerDTO("CUS" + index, "khatabookId-" + index, "1234", "Amit", "Singh", products, null);
    }

    @Test
    void createCustomer() throws Exception {


        String index = "01";
        String path = "/khatabook/khatabookId-%s/customer".formatted(index);
        CustomerDTO customerDTO = getCustomerDTO(index);
        when(myKhatabookService.getKhatabookByKhatabookId(anyString())).thenReturn(mockKhatabook);
        when(myCustomerService.save(customerDTO)).thenReturn(customerDTO);
        String newCustomerJson = mapToJson(customerDTO);
        MockHttpServletResponse response = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCustomerJson)).andReturn().getResponse();
        assertEquals(201, response.getStatus());

    }

    @Test
    void testGetCustomers() throws Exception {
        String path = "/khatabook/khatabookId-01/customers";

        Set<CustomerDTO> listOfCustomerDTO = getCustomerDTOS("01");
        when(myKhatabookService.getKhatabookByKhatabookId(anyString())).thenReturn(mockKhatabook);
        when(myCustomerService.getAll("khatabookId-01")).thenReturn(listOfCustomerDTO);
        MockHttpServletResponse response = mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertEquals(200, response.getStatus());


        KhatabookDetails khatabookDetails = new KhatabookDetails(mockKhatabookdto,
                listOfCustomerDTO, null);

        assertEquals("{\"bookId\":\"bookId\",\"khatabookId\":\"khatabookId-01\",\"numberOfCustomers\":1,\"customers\":[{\"customerId\":\"CUS01\",\"khatabookId\":\"khatabookId-01\",\"msisdn\":\"1234\",\"firstName\":\"Amit\",\"lastName\":\"Singh\",\"products\":[{\"productId\":\"milk\",\"name\":\"milk\"},{\"productId\":\"sugar\",\"name\":\"sugar\"}]}]}", response.getContentAsString());
    }

    private static Set<CustomerDTO> getCustomerDTOS(String... indexs) {
        Set<CustomerDTO> listOfCustomerDTO = new HashSet<>();
        for (String index : indexs) {
            listOfCustomerDTO.add(getCustomerDTO(index));
        }
        return listOfCustomerDTO;
    }


}