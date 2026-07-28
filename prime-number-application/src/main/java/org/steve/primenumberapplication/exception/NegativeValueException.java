package org.steve.primenumberapplication.exception;

public class NegativeValueException extends InputValueException {

    public NegativeValueException(int initialValue){
        super(String.format("Input number is invalid. Value %d is negative", initialValue), initialValue);
    }

}
