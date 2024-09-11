//package com.example.controller;
////
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.GetMapping;
////
////import com.example.service.servicee.ProductService;
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

import com.example.entity.ProductDto;
import com.example.service.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {


    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        List<Product> products = productService.getAllProduct(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add-product")
    public String showAddProductPage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProductPage(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ) {
        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile",
                    "The image file is required"));
        }
        if (result.hasErrors()) {
            return "/add-product";
        }

        //Save image file
        MultipartFile image = productDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            // Save image in 'target/classes/static/images/' to make sure it's accessible
            String uploadDir = "Public/Images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        productService.saveProduct(product);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductsCreated(@PathVariable Long id, Model model) {

        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto", productDto);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }

        return "edit-product";
    }


    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);

        return "redirect:/products";  // Redirect to a page showing all products
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute ProductDto productDto,
                                BindingResult result,
                                Model model) {


        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);

            if (result.hasErrors()) {
                System.out.println("Validation errors found: " + result.getAllErrors());
                model.addAttribute("productDto", productDto);  // Re-add productDto to the model in case of errors
                return "edit-product";  // Return to the edit-product view if there are errors
            }

            if (!productDto.getImageFile().isEmpty()) {
                // Delete the old image
                String uploadDir = "Public/Images/";
                Path oldImageFile = Paths.get(uploadDir + product.getImageFileName());

                try {
                    Files.delete(oldImageFile);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                // Save the new image
                MultipartFile image = productDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageFileName(storageFileName);
            }

            //get product from database

            Product existingProduct = productService.getProductById(id);
            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setQuantity(productDto.getQuantity());

            //save updated product object
            productService.updateProduct(existingProduct);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());

        }

        return "redirect:/products";

    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id){

        try {
            Product product = productService.getProductById(id);

            //delete product image
            Path imagePath = Paths.get("Public/Images/"  + product.getImageFileName());
            try {
                Files.delete(imagePath);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            //delete product
            productService.deleteProduct(product);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/products";
    }
}
