package com.lavrentieva;

public class InvalidLineException extends IllegalArgumentException{
    public InvalidLineException(final String message) {
        super(message);
    }
}
