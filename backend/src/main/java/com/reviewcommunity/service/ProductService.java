package com.reviewcommunity.service;

import com.reviewcommunity.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> searchProducts(String productName, String brand, String code);

    int getProductCount();
}
