package com.generic.khatabook.controller;

import com.generic.khatabook.model.OperatorDTO;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.IdGeneratorService;
import com.generic.khatabook.service.OperatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class OperatorControllerTest {

    @InjectMocks
    OperatorController operatorController;

    @Mock
    OperatorService operatorService;

    @Mock
    CustomerService customerService;

    @Mock
    IdGeneratorService idGeneratorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void operatorListShouldReturnAllOperators() {
        OperatorDTO operator1 = new OperatorDTO("opt01", "AirTel", "airtel", "234567", "airtel@airtel.com");
        OperatorDTO operator2 = new OperatorDTO("opt02", "AirCell", "aircell", "234568", "aircell@airtel.com");
        when(operatorService.getAll()).thenReturn(Arrays.asList(operator1, operator2));

        ResponseEntity<List<OperatorDTO>> response = operatorController.operatorList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void createOperatorShouldReturnCreatedOperator() {
        OperatorDTO operator = new OperatorDTO("opt01", "AirTel", "airtel", "234567", "airtel@airtel.com");

        when(operatorService.isValid(operator)).thenReturn(false);
        doNothing().when(operatorService).create(operator);

        ResponseEntity<OperatorDTO> response = operatorController.createOperator(operator);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(operator, response.getBody());
    }

    @Test
    void createOperatorShouldReturnBadRequestWhenOperatorExists() {
        OperatorDTO operator = new OperatorDTO("opt01", "AirTel", "airtel", "234567", "airtel@airtel.com");
        when(operatorService.isValid(operator)).thenReturn(true);

        ResponseEntity<OperatorDTO> response = operatorController.createOperator(operator);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getByIdShouldReturnOperator() {
        OperatorDTO operator = new OperatorDTO("opt01", "AirTel", "airtel", "234567", "airtel@airtel.com");
        when(operatorService.getByOperatorId("1")).thenReturn(operator);

        ResponseEntity<OperatorDTO> response = operatorController.getById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(operator, response.getBody());
    }

    @Test
    void deleteByIdShouldReturnOk() {
        doNothing().when(operatorService).delete("1");

        ResponseEntity response = operatorController.deleteById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateOperatorShouldReturnUpdatedOperator() {
        OperatorDTO operator = new OperatorDTO("opt01", "AirTel", "airtel", "234567", "airtel@airtel.com");
        when(operatorService.update(operator)).thenReturn(operator);

        ResponseEntity<OperatorDTO> response = operatorController.updateOperator(operator);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(operator, response.getBody());
    }
}