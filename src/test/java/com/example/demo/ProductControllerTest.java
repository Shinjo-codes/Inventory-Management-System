package com.example.demo;

import com.example.controller.ProductController;
import com.example.entity.Product;
import com.example.entity.ProductDto;
import com.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static java.nio.file.Paths.get;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductDto productDto;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

    }

    @Test
    void testGetAllProduct() throws Exception {
       Product product1 = new Product();
        Product product2 = new Product();

        List<Product> productList = Arrays.asList(product1, product2);

        //Mock the behavior of productService.getAllProduct()
        when(productService.getAllProduct(Sort.by(Sort.Direction.DESC, "id"))).thenReturn(productList);

        //Perform the GET request to /products
        mockMvc.perform((RequestBuilder) get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", productList));

        //Verify that productService.getAllProduct() was called once
        verify(productService, times(1)).getAllProduct(Sort.by(Sort.Direction.DESC, "id"));
    }


     @Test
    void testShowAddProductPage() throws Exception {
        mockMvc.perform((RequestBuilder) get("/add-product"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-product"))
                .andExpect(model().attributeExists("product"));

     }

     @Test
    void testSaveProduct() throws Exception {
        Product product = new Product();

        mockMvc.perform(post("/save-product")
                .flashAttr("product", product))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));

        verify(productService, times(1)).saveProduct(product);
     }

     @Test
        void testUpdateProduct() throws Exception {
        Product existingProduct = new Product();

        when(productService.getProductById(1L)).thenReturn(existingProduct);

        mockMvc.perform(post("/products/edit/1")
                .flashAttr("product", existingProduct))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));

        verify(productService, times(1)).updateProduct(existingProduct);
     }

     @Test
        void testEditProduct() throws Exception {
        Product editedProduct = new Product();

        when(productService.getProductById(1L)).thenReturn(editedProduct);

        mockMvc.perform((RequestBuilder) get("/products/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", editedProduct));
     }


    }





