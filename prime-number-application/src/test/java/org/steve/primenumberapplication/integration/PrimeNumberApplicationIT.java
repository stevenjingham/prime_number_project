package org.steve.primenumberapplication.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.steve.primenumberapplication.model.ErrorResponse;
import org.steve.primenumberapplication.model.PrimesResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrimeNumberApplicationIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnPrimes() {
        ResponseEntity<PrimesResponse> responseEntity = this.testRestTemplate.getForEntity(
                "/api/v1/primes/20", PrimesResponse.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(20, responseEntity.getBody().getInitialValue());
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19), responseEntity.getBody().getPrimeValues());
    }

    @Test
    void shouldReturnPrimes_whenAlgorithmCalled() {
        ResponseEntity<PrimesResponse> responseEntity = this.testRestTemplate.getForEntity(
                "/api/v1/primes/20?algorithm=SIEVE", PrimesResponse.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(20, responseEntity.getBody().getInitialValue());
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19), responseEntity.getBody().getPrimeValues());
    }

    @Test
    void shouldReturnBadRequest_NegativeInitialValue() {
        makeCallWithInvalidInitialValue("-1");
    }

    @Test
    void shouldReturnBadRequest_AboveMaximumInitialValue() {
        makeCallWithInvalidInitialValue("50000001");
    }

    @Test
    void shouldReturnBadRequest_StringInitialValue() {
        makeCallWithInvalidInitialValue("abc");
    }

    private void makeCallWithInvalidInitialValue(String initialValue) {
        String url = "/api/v1/primes/" + initialValue;
        ResponseEntity<ErrorResponse> responseEntity = this.testRestTemplate.getForEntity(
                url, ErrorResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        String expectedMessage = String.format("Invalid value '%s' for parameter 'inputValue'. Supported values are values in range [0 - 50000000]", initialValue);
        assertEquals(expectedMessage, responseEntity.getBody().getErrorMessage());
    }

    @Test
    void shouldReturnBadRequest_InvalidAlgorithmParam() {
        ResponseEntity<ErrorResponse> responseEntity = this.testRestTemplate.getForEntity(
                "/api/v1/primes/20?algorithm=sieve", ErrorResponse.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        String expectedMessage = String.format("Invalid value '%s' for parameter 'algorithm'. Supported values are [BASE, SIEVE]", "sieve");
        assertEquals(expectedMessage, responseEntity.getBody().getErrorMessage());
    }

    @Test
    void shouldReturnPrimes_XMLHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<PrimesResponse> responseEntity = this.testRestTemplate.exchange(
                        "/api/v1/primes/20", HttpMethod.GET, request, PrimesResponse.class);

        assertTrue(MediaType.APPLICATION_XML.isCompatibleWith(responseEntity.getHeaders().getContentType()));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(20, responseEntity.getBody().getInitialValue());
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19), responseEntity.getBody().getPrimeValues());
    }

}
