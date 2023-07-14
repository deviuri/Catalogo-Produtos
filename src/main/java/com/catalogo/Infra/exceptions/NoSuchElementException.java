package com.catalogo.Infra.exceptions;

public class NoSuchElementException extends RuntimeException{
    public NoSuchElementException(String msg){
        super(msg);
    }
}
