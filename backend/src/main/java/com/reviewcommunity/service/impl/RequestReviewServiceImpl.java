package com.reviewcommunity.service.impl;

import com.reviewcommunity.dto.RequestReviewDto;
import com.reviewcommunity.entity.Product;
import com.reviewcommunity.entity.RequestReview;
import com.reviewcommunity.exception.ReviewException;
import com.reviewcommunity.repository.ProductRepository;
import com.reviewcommunity.repository.RequestReviewRepository;
import com.reviewcommunity.service.RequestReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RequestReviewServiceImpl implements RequestReviewService {

    @Autowired
    private RequestReviewRepository requestReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    /*
    *   Service to add request for a review from user
    *   if review for the requested product code exists an exception
    *   is thrown, otherwise the request is saved in the request review table
    * */
    @Override
    public void addRequest(RequestReviewDto requestReviewDto) throws ReviewException {
        if (requestReviewDto.getProductName() == null || requestReviewDto.getBrand() == null || requestReviewDto.getCode() == null)
            throw new ReviewException("Error in requesting review");

        Product existingProduct = productRepository.findByCode(requestReviewDto.getCode());

        if (existingProduct != null) {
            throw new ReviewException("Product already exists");
        } else {
            RequestReview requestReview = new RequestReview();
            requestReview.setBrand(requestReviewDto.getBrand());
            requestReview.setProductName(requestReviewDto.getProductName());
            requestReview.setCode(requestReviewDto.getCode());
            requestReviewRepository.save(requestReview);
        }
    }
}
