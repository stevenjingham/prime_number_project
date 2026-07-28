package org.steve.primenumberapplication.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class SievePrimeNumberAlgorithm implements PrimeNumberAlgorithm{

    @Override
    public List<Integer> findPrimes(int initialValue) {
        log.info("using SievePrimeNumberAlgorithm to generate primes for " + initialValue);
        ArrayList<Integer> primeValues = new ArrayList<>();

        if (initialValue < 2) {
            return primeValues;
        }

        boolean[] primes = new boolean[initialValue + 1]; //Aligning index 0 to be equal to value 0
        Arrays.fill(primes, true);

        primes[0] = false;
        primes[1] = false;

        for (int i = 2; i * i <= initialValue; i++) {
            if (primes[i]) {
                for (int multiple = i * i; multiple <= initialValue; multiple += i) {
                    primes[multiple] = false;
                }
            }
        }

        for (int i = 2; i <= initialValue; i++) {
            if (primes[i]) {
                primeValues.add(i);
            }
        }

        return primeValues;
    }

}
