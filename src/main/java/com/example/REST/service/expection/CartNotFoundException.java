package com.example.REST.service.expection;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(Long id){
        super("Couldn't find cart "+id);
    }
}
