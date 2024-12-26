package com.example.antivirusbackend.service;

import com.example.antivirusbackend.entity.Product;
import com.example.antivirusbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, boolean blocked) {
        Product p = new Product();
        p.setName(name);
        p.setBlocked(blocked);
        return productRepository.save(p);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, String name, boolean blocked) {
        return productRepository.findById(id).map(prod -> {
            prod.setName(name);
            prod.setBlocked(blocked);
            return productRepository.save(prod);
        }).orElse(null);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
