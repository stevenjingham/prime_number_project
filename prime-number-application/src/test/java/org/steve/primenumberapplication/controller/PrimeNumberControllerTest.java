package org.steve.primenumberapplication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.steve.primenumberapplication.config.PrimeNumberConfiguration;
import org.steve.primenumberapplication.model.PrimeAlgorithm;
import org.steve.primenumberapplication.model.PrimesResponse;
import org.steve.primenumberapplication.service.PrimeNumberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrimeNumberController.class)
class PrimeNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrimeNumberService primeNumberService;

    @MockBean
    private PrimeNumberConfiguration primeNumberConfiguration;


    @Test
    void shouldReturnPrimesUsingDefaultAlgorithm() throws Exception {

        when(primeNumberService.getPrimes(20, PrimeAlgorithm.BASE))
                .thenReturn(new PrimesResponse(20, List.of(2, 3, 5, 7, 11, 13, 17, 19)));

        mockMvc.perform(get("/api/v1/primes/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.initialValue").value(20))
                .andExpect(jsonPath("$.primeValues[0]").value(2));

        verify(primeNumberService)
                .getPrimes(20, PrimeAlgorithm.BASE);
    }


    @Test
    void shouldUseRequestedAlgorithm() throws Exception {

        when(primeNumberService.getPrimes(20, PrimeAlgorithm.SIEVE))
                .thenReturn(new PrimesResponse(20, List.of(2, 3, 5, 7, 11, 13, 17, 19)));

        mockMvc.perform(
                        get("/api/v1/primes/20?algorithm=SIEVE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.initialValue").value(20))
                .andExpect(jsonPath("$.primeValues[0]").value(2));

        verify(primeNumberService)
                .getPrimes(20, PrimeAlgorithm.SIEVE);
    }


    @Test
    void shouldRejectInvalidAlgorithm() throws Exception {

        mockMvc.perform(get("/api/v1/primes/20?algorithm=abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(primeNumberService);
    }


    @Test
    void shouldRejectNonIntegerPathVariable() throws Exception {

        mockMvc.perform(get("/api/v1/primes/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(primeNumberService);
    }
}
