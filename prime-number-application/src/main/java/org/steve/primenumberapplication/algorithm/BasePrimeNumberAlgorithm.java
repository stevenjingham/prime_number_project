package org.steve.primenumberapplication.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
public class BasePrimeNumberAlgorithm implements PrimeNumberAlgorithm{

    @Override
    public List<Integer> findPrimes(int inputNumber) {
        log.info("using BasePrimeNumberAlgorithm");
        List<Integer> primeValues = new ArrayList<>();

        if (inputNumber < 2){
            return primeValues;
        }

        primeValues = IntStream.range(2, inputNumber+1).parallel().filter(this::isPrime).boxed().toList();

        return primeValues;
    }

    public boolean isPrime(int number){
        int square_number = (int) Math.sqrt(number);
        return IntStream.range(2, square_number+1).noneMatch(n -> number % n == 0);
    }

}
