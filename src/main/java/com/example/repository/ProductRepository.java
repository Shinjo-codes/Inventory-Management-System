package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Corrected method signature
    Optional<Product> findById(Long id);
}
