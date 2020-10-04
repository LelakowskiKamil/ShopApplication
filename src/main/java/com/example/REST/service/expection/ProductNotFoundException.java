package com.example.REST.service.expection;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id){
        super("Couldn't find product "+id);
    }
}
