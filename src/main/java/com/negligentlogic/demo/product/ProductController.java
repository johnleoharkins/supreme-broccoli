package com.negligentlogic.demo.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/products")
@CrossOrigin
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping(path = "/add-new-product")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String addProduct(@RequestBody Map<String, Object> payload) {
        logger.info("add product");

        logger.info(payload.toString());
        productService.addProduct((String) payload.get("name"), (String) payload.get("description"), (double) payload.get("price"));
        if (true){
            return "Product added successfully";
        }else{
            return "Product could not be added";
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping(path = "/id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable("id") Long id){
        return productService.getProductById(id);
    }

    @PostMapping(path = "/id/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.updateProduct(id, product);
    }


}
