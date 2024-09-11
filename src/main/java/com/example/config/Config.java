package com.example.config;

import com.example.repository.UserRepository;
import com.example.service.ProductService;
import com.example.service.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
    public class Config {

        @Bean
        public ProductService productService() {
            return new ProductServiceImpl();
        }

//        @Configuration
//        public class JpaConfig {

            @Bean
            public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                    DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
                LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
                em.setDataSource(dataSource);
                em.setPackagesToScan("com.example.entity");
                em.setJpaVendorAdapter(jpaVendorAdapter);
                return em;
            }
        }


//        @Bean
//        public UserRepository userRepository() {
//
//            return null;
//        }


