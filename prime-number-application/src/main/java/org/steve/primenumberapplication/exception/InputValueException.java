package org.steve.primenumberapplication.exception;

import lombok.Getter;

@Getter
public class InputValueException extends IllegalArgumentException {

    private final int inputValue;

    public InputValueException(String message, int inputValue){
        super(message);
        this.inputValue = inputValue;
    }

}
