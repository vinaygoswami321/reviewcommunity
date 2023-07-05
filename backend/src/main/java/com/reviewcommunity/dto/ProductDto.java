package com.reviewcommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long productId;

    private String productName;

    private String brand;

    private String code;

    private double averageRating;

    private long numberOfReviews;

}
