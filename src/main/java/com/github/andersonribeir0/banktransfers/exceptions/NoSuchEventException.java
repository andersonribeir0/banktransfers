package com.github.andersonribeir0.banktransfers.exceptions;


import java.rmi.NoSuchObjectException;

public class NoSuchEventException extends RuntimeException {

    public NoSuchEventException(String s) {
        super(s);
    }
}
