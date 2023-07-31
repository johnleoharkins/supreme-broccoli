package com.negligentlogic.demo.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public boolean addProduct(String name, String description, double price){
        Product product = new Product(name,description, 0, price);
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }

    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public void updateProduct(Long id, Product updatedProduct) {
        try {
            Product product = productRepository.findById(id).get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            productRepository.save(product);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional;
        try {
            productOptional = productRepository.findById(id);
            if(productOptional.isPresent()){
                return productOptional.get();
            }else{
                throw new ProductNotFoundException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
