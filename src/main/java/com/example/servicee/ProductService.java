package com.example.servicee;

import java.util.List;

import com.example.entity.Product;

public interface ProductService {
    List<Product> getAllProduct();

    Product saveProduct(Product product);

	Product getProductById(Long id);

	Product updateProduct(Product product);
}