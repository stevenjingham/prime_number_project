package org.steve.primenumberapplication.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class ErrorResponse {

    @Getter
    @Schema(example = "Invalid value '-20' for parameter 'inputValue'. Supported values are values in range [0 - 50000000]")
    private final String errorMessage;

    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }


}
