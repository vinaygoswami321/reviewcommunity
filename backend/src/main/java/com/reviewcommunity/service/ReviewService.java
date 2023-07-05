package com.reviewcommunity.service;

import com.reviewcommunity.dto.ReviewDto;
import com.reviewcommunity.entity.Review;
import com.reviewcommunity.exception.ReviewException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ReviewService {
    List<ReviewDto> getApprovedReviews(long productId);

    List<ReviewDto> getUnApprovedReviews();

    int getReviewCount();

    Double getAverageRating(long productId);

    Long getNumberOfReviews(long productId);

    Review addReview(ReviewDto reviewDto, long productId, HttpServletRequest request) throws ReviewException;

    void approveReview(long reviewId) throws ReviewException;

    void rejectReview(long reviewId) throws ReviewException;
}
