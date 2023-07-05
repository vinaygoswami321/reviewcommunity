package com.reviewcommunity.service.impl;

import com.reviewcommunity.dto.StatsDto;
import com.reviewcommunity.repository.ProductRepository;
import com.reviewcommunity.repository.ReviewRepository;
import com.reviewcommunity.repository.UserRepository;
import com.reviewcommunity.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    /*
    *   Service to retrieve stats : number of registered user using the user repository,
    *   number of products using the product repository, and number of reviews using the
    *   review repository and return a status dto
    * */
    @Override
    public StatsDto getStats() {
        return new StatsDto(userRepository.getUserCount(), productRepository.getProductCount(), reviewRepository.getReviewCount());
    }
}
