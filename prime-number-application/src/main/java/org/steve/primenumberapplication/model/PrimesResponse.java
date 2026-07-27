package org.steve.primenumberapplication.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

public class PrimesResponse {

    @Getter
    @Schema(example = "20")
    private final int initialValue;

    @Getter
    @Schema(example = "[2,3,5,7,11,13,17,19]")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "primeValue")
    private final List<Integer> primeValues;

    public PrimesResponse(int initialValue, List<Integer> primeValues){
        this.initialValue = initialValue;
        this.primeValues = primeValues;
    }


}
