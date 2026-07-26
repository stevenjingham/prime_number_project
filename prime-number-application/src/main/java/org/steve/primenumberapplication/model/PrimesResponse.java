package org.steve.primenumberapplication.model;

import lombok.Getter;

import java.util.List;

public class PrimesResponse {

    @Getter
    private final int initialValue;
    @Getter
    private final List<Integer> primeValues;

    public PrimesResponse(int initialValue, List<Integer> primeValues){
        this.initialValue = initialValue;
        this.primeValues = primeValues;
    }


}
