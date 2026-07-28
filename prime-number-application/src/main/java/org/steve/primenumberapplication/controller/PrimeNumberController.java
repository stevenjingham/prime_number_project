package org.steve.primenumberapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.steve.primenumberapplication.model.ErrorResponse;
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

    @Operation(summary = "Get list of prime numbers below, or equal to, the inputted initial value.",
                description = "Returns the initial value, and a list of prime numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/primes/{initialValue}")
    public ResponseEntity<PrimesResponse> getPrimes(
            @PathVariable @Parameter(name = "initialValue", description = "Value to provide prime numbers less than, or equal to, the input", example = "20") int initialValue,
            @RequestParam(required=false, defaultValue = "BASE") @Parameter(name = "algorithm", description = "Prime number algorithm selection") PrimeAlgorithm algorithm
    ) {

        return new ResponseEntity<>(primeNumberService.getPrimes(initialValue, algorithm), HttpStatus.OK);
    }

}
