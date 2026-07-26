package org.steve.primenumberapplication.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SievePrimeNumberAlgorithmTest {

    private final PrimeNumberAlgorithm primeNumberAlgorithm = new SievePrimeNumberAlgorithm();


    @Test
    void findPrimes_shouldReturnPrimesUpToNumber() {
        List<Integer> result = primeNumberAlgorithm.findPrimes(20);

        assertEquals(
                List.of(2, 3, 5, 7, 11, 13, 17, 19),
                result
        );
    }

    @Test
    void findPrimes_shouldReturnPrimesUpToLargeNumber() {
        List<Integer> result = primeNumberAlgorithm.findPrimes(200000);

        assertEquals(17984, result.size());

        assertEquals(2, result.get(0));
        assertEquals(199999, result.get(result.size()-1));
    }


    @Test
    void findPrimes_shouldIncludeInputNumberWhenInputIsPrime() {
        List<Integer> result = primeNumberAlgorithm.findPrimes(11);

        assertTrue(result.contains(11));
    }


    @Test
    void findPrimes_shouldReturnEmptyListForZero() {
        List<Integer> result = primeNumberAlgorithm.findPrimes(0);

        assertTrue(result.isEmpty());
    }


    @Test
    void findPrimes_shouldReturnEmptyListForOne() {
        List<Integer> result = primeNumberAlgorithm.findPrimes(1);

        assertTrue(result.isEmpty());
    }


    @Test
    void findPrimes_shouldReturnOnlyPrimeForSmallInput() {
        List<Integer> result = primeNumberAlgorithm.findPrimes(2);

        assertEquals(
                List.of(2),
                result
        );
    }



}
