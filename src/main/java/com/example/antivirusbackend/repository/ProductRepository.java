package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
