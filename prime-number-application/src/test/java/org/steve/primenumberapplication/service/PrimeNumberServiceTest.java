package org.steve.primenumberapplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.steve.primenumberapplication.algorithm.BasePrimeNumberAlgorithm;
import org.steve.primenumberapplication.algorithm.SievePrimeNumberAlgorithm;
import org.steve.primenumberapplication.config.PrimeNumberConfiguration;
import org.steve.primenumberapplication.exception.MaximumValueExceededException;
import org.steve.primenumberapplication.exception.NegativeValueException;
import org.steve.primenumberapplication.model.PrimeAlgorithm;
import org.steve.primenumberapplication.model.PrimesResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrimeNumberServiceTest {

    @Mock
    private BasePrimeNumberAlgorithm baseAlgorithm;

    @Mock
    private SievePrimeNumberAlgorithm sieveAlgorithm;

    @Mock
    private PrimeNumberConfiguration primeNumberConfiguration;

    private PrimeNumberService primeNumberService;

    @BeforeEach
    public void setUp(){
        lenient().when(primeNumberConfiguration.getMaximumInputValue()).thenReturn(1000);

        primeNumberService = new PrimeNumberService(baseAlgorithm, sieveAlgorithm, primeNumberConfiguration);
    }

    @Test
    public void useCorrectAlgorithmGivenInput_Base(){
        when(baseAlgorithm.findPrimes(20))
                .thenReturn(List.of(2, 3, 5, 7, 11, 13, 17, 19));

        PrimesResponse response = primeNumberService.getPrimes(20, PrimeAlgorithm.BASE);

        assertEquals(
                List.of(2, 3, 5, 7, 11, 13, 17, 19),
                response.getPrimeValues()
        );
        assertEquals(
                20,
                response.getInitialValue()
        );
        verify(baseAlgorithm).findPrimes(20);
        verifyNoInteractions(sieveAlgorithm);
    }

    @Test
    public void useCorrectAlgorithmGivenInput_Sieve(){
        when(sieveAlgorithm.findPrimes(20))
                .thenReturn(List.of(2, 3, 5, 7, 11, 13, 17, 19));

        PrimesResponse response = primeNumberService.getPrimes(20, PrimeAlgorithm.SIEVE);

        assertEquals(
                List.of(2, 3, 5, 7, 11, 13, 17, 19),
                response.getPrimeValues()
        );
        assertEquals(
                20,
                response.getInitialValue()
        );
        verify(sieveAlgorithm).findPrimes(20);
        verifyNoInteractions(baseAlgorithm);
    }

    @Test
    public void getPrimes_allowsZeroInputValue(){
        when(sieveAlgorithm.findPrimes(0))
                .thenReturn(List.of());

        PrimesResponse response = primeNumberService.getPrimes(0, PrimeAlgorithm.SIEVE);

        assertTrue(
                response.getPrimeValues().isEmpty()
        );
        assertEquals(
                0,
                response.getInitialValue()
        );
        verify(sieveAlgorithm).findPrimes(0);
        verifyNoInteractions(baseAlgorithm);
    }

    @Test
    public void getPrimes_allowsMaximumInputValue(){
        when(sieveAlgorithm.findPrimes(1000))
                .thenReturn(List.of());

        PrimesResponse response = primeNumberService.getPrimes(1000, PrimeAlgorithm.SIEVE);

        assertEquals(
        1000,
                response.getInitialValue()
        );
        verify(sieveAlgorithm).findPrimes(1000);
        verifyNoInteractions(baseAlgorithm);
    }

    @Test
    public void getPrimes_throwsWhenNegativeInputValue(){
        assertThrows(NegativeValueException.class, () -> primeNumberService.getPrimes(-1, PrimeAlgorithm.SIEVE));
        verifyNoInteractions(baseAlgorithm);
        verifyNoInteractions(sieveAlgorithm);
    }

    @Test
    public void getPrimes_throwsWhenOverMaximumInputValue(){
        assertThrows(MaximumValueExceededException.class, () -> primeNumberService.getPrimes(1001, PrimeAlgorithm.SIEVE));
        verifyNoInteractions(baseAlgorithm);
        verifyNoInteractions(sieveAlgorithm);
    }

}
