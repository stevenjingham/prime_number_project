package org.steve.primenumberapplication.exception;

public class MaximumValueExceededException extends InputValueException {

    public MaximumValueExceededException(int inputValue, int maximumValue){
        super(String.format("Input number is invalid. Value %d is above maximum allowable value of %d", inputValue, maximumValue), inputValue);
    }

}
