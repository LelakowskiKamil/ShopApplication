package com.example.REST.service.controller;

import com.example.REST.service.*;
import com.example.REST.service.expection.CustomerNotFoundException;
import com.example.REST.service.expection.ProductNotFoundException;
import com.example.REST.service.model.Product;
import com.example.REST.service.model.assembler.ProductModelAssembler;
import com.example.REST.service.repository.ProductRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductModelAssembler assembler;

    public ProductController(ProductRepository productRepository, ProductModelAssembler assembler) {
        this.productRepository = productRepository;
        this.assembler = assembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> showAll() {
        List<EntityModel<Product>> orders = productRepository.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(orders,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).all()).withSelfRel());
    }

    @PostMapping("/products")
    public ResponseEntity<EntityModel<Product>> newProduct(@RequestBody Product newProduct) {
        EntityModel<Product> entityModel = assembler.toModel(productRepository.save(newProduct));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //Single item
    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(product);
    }

//    @DeleteMapping("/orders/{id}")
//    ResponseEntity<?> delete(@PathVariable Long id) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(()-> new ProductNotFoundException(id));
//
//        return ResponseEntity
//                .status(HttpStatus.METHOD_NOT_ALLOWED)
//                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
//                .body(Problem.create()
//                        .withTitle("Method not allowed")
//                        .withDetail("You can't complete an order that is in the " + product.getStatus() + " status"));
//
//    }
}
