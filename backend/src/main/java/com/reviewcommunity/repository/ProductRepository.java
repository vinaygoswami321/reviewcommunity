package com.reviewcommunity.repository;

import com.reviewcommunity.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContainingOrBrandContainingOrCodeContaining(String productName, String brand, String code);
    @Query("SELECT COUNT(P) FROM Product P")
    int getProductCount();

    Product findByProductId(long productId);

    Product findByCode(String code);

    List<Product> findByProductNameEqualsIgnoreCase(String productName);

    List<Product> findByBrandEqualsIgnoreCase(String productBrand);

    List<Product> findByCodeEqualsIgnoreCase(String productCode);

    List<Product> findByProductNameEqualsIgnoreCaseAndBrandEqualsIgnoreCase(String productName, String productBrand);

    List<Product> findByProductNameEqualsIgnoreCaseAndCodeEqualsIgnoreCase(String productName, String productCode);

    List<Product> findByBrandEqualsIgnoreCaseAndCodeEqualsIgnoreCase(String productBrand, String productCode);

    List<Product> findByProductNameEqualsIgnoreCaseAndBrandEqualsIgnoreCaseAndCodeEqualsIgnoreCase(String productName, String productBrand, String productCode);
}
