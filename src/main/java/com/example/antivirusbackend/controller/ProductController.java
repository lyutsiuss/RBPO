package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.Product;
import com.example.antivirusbackend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: 1. Добавить авторизацию

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestParam String name,
                                 @RequestParam boolean blocked) {
        return productService.createProduct(name, blocked);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam boolean blocked) {
        return productService.updateProduct(id, name, blocked);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? "Product deleted" : "Product not found";
    }
}
