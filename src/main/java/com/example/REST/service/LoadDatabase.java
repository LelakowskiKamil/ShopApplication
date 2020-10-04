package com.example.REST.service;

import com.example.REST.service.model.Customer;
import com.example.REST.service.model.Product;
import com.example.REST.service.model.ShoppingCart;
import com.example.REST.service.repository.CustomerRepository;
import com.example.REST.service.repository.ProductRepository;
import com.example.REST.service.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(RestServiceApplication.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository,
                                   ProductRepository productRepository,
                                   ShoppingCartRepository shoppingCartRepository) {
        return args -> {
            Customer c1 = new Customer("Kamil", "Lelakowski");
            Customer c2 = new Customer("Jan", "Niezbedny");
            customerRepository.save(c1);
            customerRepository.save(c2);

            customerRepository.findAll().forEach(employee -> log.info("New Customer: " + employee));
            Product o1 = new Product("Macbook", "Laptop od firmy Apply", 2999.99);
            Product o2 = new Product("Iphone", "Telefon od firmy Apple", 1279.99);
            Product o3 = new Product("Hauwei P20", "Telefon od firmy Hauwei", 679.99);
            productRepository.save(o1);
            productRepository.save(o2);
            productRepository.save(o3);

            productRepository.findAll().forEach(product -> log.info("New Product: " + product));

            shoppingCartRepository.save(new ShoppingCart(o1));
            shoppingCartRepository.save(new ShoppingCart(o1));
            shoppingCartRepository.save(new ShoppingCart(o1));

            shoppingCartRepository.findAll().forEach(cart -> log.info("New Product in shopping cart " + cart));

        };
    }

}
