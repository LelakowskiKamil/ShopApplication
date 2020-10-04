package com.example.REST.service.controller;

import com.example.REST.service.model.assembler.CartModelAssembler;
import com.example.REST.service.expection.CustomerNotFoundException;
import com.example.REST.service.repository.ShoppingCartRepository;
import com.example.REST.service.model.ShoppingCart;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShoppingCartController {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartModelAssembler assembler;

    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository, CartModelAssembler assembler) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.assembler = assembler;
    }


    @GetMapping("/carts")
    public CollectionModel<EntityModel<ShoppingCart>> all() {
        List<EntityModel<ShoppingCart>> shoppingCart = shoppingCartRepository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(shoppingCart,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShoppingCartController.class).all()).withSelfRel());
    }

    @GetMapping("/carts/{id}")
    public EntityModel<ShoppingCart> one(@PathVariable Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(shoppingCart);
    }


}
