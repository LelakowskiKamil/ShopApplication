package com.example.REST.service.controller;

import com.example.REST.service.expection.CartNotFoundException;
import com.example.REST.service.expection.ProductNotFoundException;
import com.example.REST.service.model.Product;
import com.example.REST.service.model.assembler.CartModelAssembler;
import com.example.REST.service.expection.CustomerNotFoundException;
import com.example.REST.service.repository.ShoppingCartRepository;
import com.example.REST.service.model.ShoppingCart;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/carts")
    public ResponseEntity<EntityModel<ShoppingCart>> newShoppingCart(@RequestBody ShoppingCart newShoppingCart) {
        EntityModel<ShoppingCart> entityModel = assembler.toModel(shoppingCartRepository.save(newShoppingCart));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/carts/{id}")
    public EntityModel<ShoppingCart> one(@PathVariable Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(shoppingCart);
    }

    @PutMapping("/carts/{id}")
    public ResponseEntity<?> replaceShoppingCart(@RequestBody ShoppingCart newShoppingCart, @PathVariable Long id) {
        ShoppingCart updatedShoppingCart = shoppingCartRepository.findById(id)
                .map(shoppingCart -> {
                    shoppingCart.setCartOrder(
                            new Product(
                                    shoppingCart.getCartOrder().getName(),
                                    shoppingCart.getCartOrder().getDescription(),
                                    shoppingCart.getCartOrder().getPrice()));
                    return shoppingCartRepository.save(shoppingCart);
                })
                .orElseGet(() -> {
                    newShoppingCart.setId(id);
                    return shoppingCartRepository.save(newShoppingCart);
                });

        EntityModel<ShoppingCart> entityModel = assembler.toModel(updatedShoppingCart);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<?> deleteShoppingCart(@PathVariable Long id) {
        if (shoppingCartRepository.findById(id).isPresent()) {
            shoppingCartRepository.deleteById(id);
        } else {
            throw new CartNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }


}
