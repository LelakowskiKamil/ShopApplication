package com.example.REST.service.model.assembler;

import com.example.REST.service.controller.ShoppingCartController;
import com.example.REST.service.model.ShoppingCart;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<ShoppingCart, EntityModel<ShoppingCart>> {
    @Override
    public EntityModel<ShoppingCart> toModel(ShoppingCart shoppingCart) {
         EntityModel<ShoppingCart> cartModel = EntityModel.of(shoppingCart,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShoppingCartController.class).one(shoppingCart.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ShoppingCartController.class).all()).withRel("carts"));


        return cartModel;
    }
    }

