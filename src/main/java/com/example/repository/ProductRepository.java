package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
   
    Optional<Product> findById(Long id);
}
