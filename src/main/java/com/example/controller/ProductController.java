//package com.example.controller;
////
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.GetMapping;
////
////import com.example.servicee.ProductService;
////
////@Controller
////public class ProductController {
////    private ProductService productService;
////
////    public ProductController(ProductService productService) {
////        super();
////        this.productService= productService;
////
////    }
////    //handler method to handle list products and return list view mode
////    @GetMapping("/product")
////    public String listProduct(Model model) {
////        model.addAttribute("Products", productService.getAllProduct());
////        return "products";
////
////    }
////
////}


package com.example.controller;

import com.example.entity.Product;

import com.example.servicee.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String viewProducts(Model model) {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "products";
    }
    
    @GetMapping ("/add-product")
    public String showAddProductPage(Model model) {
    	model.addAttribute("product", new Product());
    	return "add-product";
    }
    
    @GetMapping("/products/edit/{id}")
	public String editProductsCreated(@PathVariable Long id, Model model) {
		model.addAttribute("product", productService.getProductById(id));
		return "edit-product";
		
	}
    
   @PostMapping("/save-product")
   public String saveProduct(@ModelAttribute Product product) {
   productService.saveProduct(product);
   return "redirect:/products";  // Redirect to a page showing all products
        }
   
   @PostMapping("/products/edit/{id}")
	public String updateProduct(@PathVariable Long id,
			@ModelAttribute("product") Product product,
			Model model) {
		
		//get product from database
		
		Product existingProduct = productService.getProductById(id);
		existingProduct.setName(product.getName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setId(product.getId());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setQuantity(product.getQuantity());
		
		//save updated product object
		productService.updateProduct(existingProduct);
		return "redirect:/products";
    }
}
