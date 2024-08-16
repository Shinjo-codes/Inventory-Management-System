package com.example.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.servicee.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> getAllProduct(){
        //
        return productRepository.findAll();
    }


	@Override
	public Product saveProduct(Product product) {
		
		return productRepository.save(product);

}


	@Override
	public Product getProductById(Long id) {
		
		return productRepository.findById(id).get();
	}


	@Override
	public Product updateProduct(Product product) {
		
		return productRepository.save(product);
	}
}