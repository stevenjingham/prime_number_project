package org.steve.primenumberapplication.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.steve.primenumberapplication.algorithm.BasePrimeNumberAlgorithm;
import org.steve.primenumberapplication.algorithm.SievePrimeNumberAlgorithm;
import org.steve.primenumberapplication.config.PrimeNumberConfiguration;
import org.steve.primenumberapplication.exception.MaximumValueExceededException;
import org.steve.primenumberapplication.exception.NegativeValueException;
import org.steve.primenumberapplication.model.PrimeAlgorithm;
import org.steve.primenumberapplication.model.PrimesResponse;

@Service
@Slf4j
@EnableCaching
public class PrimeNumberService {

    private final BasePrimeNumberAlgorithm basePrimeNumberAlgorithm;
    private final SievePrimeNumberAlgorithm sievePrimeNumberAlgorithm;
    private final PrimeNumberConfiguration primeNumberConfiguration;

    public PrimeNumberService(BasePrimeNumberAlgorithm basePrimeNumberAlgorithm, SievePrimeNumberAlgorithm sievePrimeNumberAlgorithm, PrimeNumberConfiguration primeNumberConfiguration) {
        this.basePrimeNumberAlgorithm = basePrimeNumberAlgorithm;
        this.sievePrimeNumberAlgorithm = sievePrimeNumberAlgorithm;
        this.primeNumberConfiguration = primeNumberConfiguration;
    }

    @Cacheable(value = "primes_cache", key = "#initialValue")
    public PrimesResponse getPrimes (int initialValue, PrimeAlgorithm algorithm){

        validateInitialValue(initialValue);

        return switch (algorithm){
            case SIEVE -> new PrimesResponse(initialValue, sievePrimeNumberAlgorithm.findPrimes(initialValue));
            case BASE -> new PrimesResponse(initialValue, basePrimeNumberAlgorithm.findPrimes(initialValue));
        };
    }

    private void validateInitialValue(int initialValue) {
        if (initialValue < 0){
            log.warn("Input number {} is invalid. Value is negative", initialValue);
            throw new NegativeValueException(initialValue);
        }
        if (initialValue > primeNumberConfiguration.getMaximumInputValue()){
            log.warn("Input number {} is invalid. Value is over maximum value", initialValue);
            throw new MaximumValueExceededException(initialValue, primeNumberConfiguration.getMaximumInputValue());
        }
    }


}
