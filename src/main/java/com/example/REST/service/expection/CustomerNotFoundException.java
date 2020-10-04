package com.example.REST.service.expection;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id){
        super("Couldn't find customer "+id);
    }
}
