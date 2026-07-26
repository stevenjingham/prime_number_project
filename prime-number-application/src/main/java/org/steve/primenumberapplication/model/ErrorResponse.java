package org.steve.primenumberapplication.model;

import lombok.Getter;

public class ErrorResponse {

    @Getter
    private final String errorMessage;

    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }


}
