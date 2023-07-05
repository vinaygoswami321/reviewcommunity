package com.reviewcommunity.controller;

import com.reviewcommunity.dto.ProductDto;
import com.reviewcommunity.repository.ProductRepository;
import com.reviewcommunity.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    /*
    *   Search product by giving query
    *   on successful search, return
    *   a list of products found
    * */
    @GetMapping("/product/search/{query}")
    public ResponseEntity<?> searchProducts(
            @PathVariable("query") String query
    ) {
          List<ProductDto> productDtos = productService.searchProducts(query,query,query);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    /*
    *   Get a product by its product code
    *   if no product exist for the given
    *   product code return not found status code
    * */
    @GetMapping("/get/product/{productCode}")
    public ResponseEntity<?> getProduct(@PathVariable("productCode") String productCode){
        ProductDto productDto = productService.getProduct(productCode);
        if(productDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
}
