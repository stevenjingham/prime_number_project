package org.steve.primenumberapplication.exception;

public class NegativeValueException extends InputValueException {

    public NegativeValueException(int inputNumber){
        super(String.format("Input number is invalid. Value %d is negative", inputNumber), inputNumber);
    }

}
