package com.reviewcommunity.service.impl;

import com.reviewcommunity.dto.ProductDto;
import com.reviewcommunity.entity.Product;
import com.reviewcommunity.repository.ProductRepository;
import com.reviewcommunity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewServiceImpl reviewService;

    /*
    *   Service to search products by product name, brand or code
    *   returns a list of products found
    * */
    @Override
    public List<ProductDto> searchProducts(String productName, String brand, String code) {
        List<Product> products = productRepository.findByProductNameContainingOrBrandContainingOrCodeContaining(productName, brand, code);

        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            Double averageRating = reviewService.getAverageRating(product.getProductId()) == null ? 0.0 : reviewService.getAverageRating(product.getProductId());
            Long numberOfReviews = reviewService.getNumberOfReviews(product.getProductId()) == null ? 0 : reviewService.getNumberOfReviews(product.getProductId());

            productDtos.add(new ProductDto(product.getProductId(), product.getProductName(), product.getBrand(),
                    product.getCode(), averageRating, numberOfReviews));
        }

        return productDtos;
    }

    /*
    *  Service to get the number of products in the product talbe
    * */
    @Override
    public int getProductCount() {
        return productRepository.getProductCount();
    }

    /*
    *   Retrieve product by its product code
    *   return null if product doesn't exit for the given product code
    *   and product dto after mapping from the retrieved product when product exits
    *   for the given product code
    * */
    public ProductDto getProduct(String productCode){
        Product product =  productRepository.findByCode(productCode);
        if(product == null) return null;

        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setBrand(product.getBrand());
        productDto.setCode(product.getCode());

        return productDto;
    }
}
