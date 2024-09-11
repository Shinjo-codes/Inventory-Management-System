package com.example.service;

import com.example.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProduct(Sort sort) ;

    void saveProduct(Product product);

	Product getProductById(Long id);

	void updateProduct(Product product);

    void deleteProduct(Product product);
}