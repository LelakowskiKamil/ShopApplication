package com.example.REST.service.expection;

public class CartNotFoundException extends RuntimeException {

    CartNotFoundException(Long id){
        super("Couldn't find order "+id);
    }
}
