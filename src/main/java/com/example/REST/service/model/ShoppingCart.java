package com.example.REST.service.model;

import javax.persistence.*;

@Entity
@Table(name = "SHOPPING_CARTS")
public class ShoppingCart {

    private @Id @GeneratedValue Long id;

    @ManyToOne
    private Product cartProduct;

    public ShoppingCart() {
    }

    public ShoppingCart(Product cartProduct) {
        this.cartProduct = cartProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getCartOrder() {
        return cartProduct;
    }

    public void setCartOrder(Product product) {
        this.cartProduct = product;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", order="  + cartProduct +
                '}';
    }
}
