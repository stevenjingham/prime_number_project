package org.steve.primenumberapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.steve.primenumberapplication.model.PrimeAlgorithm;
import org.steve.primenumberapplication.model.PrimesResponse;
import org.steve.primenumberapplication.service.PrimeNumberService;

@RestController
@RequestMapping("/api/v1")
public class PrimeNumberController {

    private final PrimeNumberService primeNumberService;

    public PrimeNumberController(PrimeNumberService primeNumberService) {
        this.primeNumberService = primeNumberService;
    }

    @GetMapping("/primes/{initialValue}")
    public ResponseEntity<PrimesResponse> getPrimes(
            @PathVariable int initialValue,
            @RequestParam(required=false, defaultValue = "BASE") PrimeAlgorithm algorithm) {

        return new ResponseEntity<>(primeNumberService.getPrimes(initialValue, algorithm), HttpStatus.OK);
    }



}
