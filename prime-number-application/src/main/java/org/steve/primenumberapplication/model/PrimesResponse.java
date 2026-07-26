package org.steve.primenumberapplication.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;

import java.util.List;

public class PrimesResponse {

    @Getter
    private final int initialValue;
    @Getter
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "primeValue")
    private final List<Integer> primeValues;

    public PrimesResponse(int initialValue, List<Integer> primeValues){
        this.initialValue = initialValue;
        this.primeValues = primeValues;
    }


}
