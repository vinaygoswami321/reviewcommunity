package com.reviewcommunity.service.impl;

import com.reviewcommunity.dto.ReviewDto;
import com.reviewcommunity.entity.Product;
import com.reviewcommunity.entity.Review;
import com.reviewcommunity.entity.User;
import com.reviewcommunity.exception.ReviewException;
import com.reviewcommunity.repository.ProductRepository;
import com.reviewcommunity.repository.ReviewRepository;
import com.reviewcommunity.repository.UserRepository;
import com.reviewcommunity.service.ReviewService;
import com.reviewcommunity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    /*
    *   Service to retrieve approved reviews using the product id
    *   for the product and returns a list of reviews
    * */
    @Override
    public List<ReviewDto> getApprovedReviews(long productId) {
        List<Review> reviews = reviewRepository.findByProductProductIdAndIsApprovedTrue(productId);
        List<ReviewDto> approvedReviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto(review.getReviewId(), review.getReviewHeading(), review.getReviewComment(),
                    review.getRating(), review.getUser().getUserId(),
                    review.getUser().getFirstName(), review.getUser().getLastName());

            approvedReviewDtos.add(reviewDto);
        }

        return approvedReviewDtos;
    }

    /*
    *   Service to retrieve un approved reviews
    *   and returns a list of unapproved reviews
    * */
    @Override
    public List<ReviewDto> getUnApprovedReviews() {
        List<Review> reviews = reviewRepository.findByIsApprovedFalse();

        List<ReviewDto> unApprovedReviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto(review.getReviewId(), review.getReviewHeading(), review.getReviewComment(),
                    review.getRating(), review.getUser().getUserId(),
                    review.getUser().getFirstName(), review.getUser().getLastName());

            unApprovedReviewDtos.add(reviewDto);
        }

        return unApprovedReviewDtos;
    }

    /*
    *   Service to get the number of reviews for all the products
    * */
    @Override
    public int getReviewCount() {
        return reviewRepository.getReviewCount();
    }

    /*
    *   Service to get the average rating for a product
    *   using its product id, from only the approved reviews
    *   for that product
    * */
    @Override
    public Double getAverageRating(long productId) {
        return reviewRepository.getAverageRatingByProductId(productId);
    }

    /*
    *  Service to get the number of reviews for a specific product
    *  using its product id
    * */
    @Override
    public Long getNumberOfReviews(long productId) {
        return reviewRepository.getReviewCountByProductId(productId);
    }

    /*
    *   Service to add a new review for a product using its product id,
    *   review dto and retrieving the user details from the jwt token
    *   passed with the http request, if no product is found for the
    *   given product id an exception is thrown else a new review gets
    *   added for the product
    * */
    @Override
    public Review addReview(ReviewDto reviewDto, long productId, HttpServletRequest request) throws ReviewException {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractUserName(token);
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findByProductId(productId);

        if (product == null)
            throw new ReviewException(("There is no product with the product id :" + productId + "unable to post review"));
        Review newReview = mapDtoToEntity(reviewDto);
        newReview.setProduct(product);
        newReview.setUser(user);
        return reviewRepository.save(newReview);
    }

    /*
    *   Service to approve a review by passing the review id,
    *   if a review doesn't exist for the given review id
    *   an exception is thrown else the review's is approved field
    *   is set to true
    * */
    @Override
    public void approveReview(long reviewId) throws ReviewException {
        try {
            Optional<Review> existingReview = reviewRepository.findById(reviewId);
            if (existingReview.isPresent()) {
                Review review = existingReview.get();

                review.setIsApproved(true);
                reviewRepository.save(review);
            } else {
                throw new ReviewException("Review not found");
            }
        } catch (Exception e) {
            throw new ReviewException(("Unable to approve review"));
        }
    }

    /*
    *   Service to reject a review using its review id,
    *   if a review for the given review id is present in
    *   the table it gets deleted else an exception is thrown
    * */
    @Override
    public void rejectReview(long reviewId) throws ReviewException {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);

        if (existingReview.isPresent()) {
            reviewRepository.delete(existingReview.get());
        } else {
            throw new ReviewException("review not found");
        }
    }

    /*
    *   Method to map review dto to a review object
    *   and returned the mapped review object
    * */
    private Review mapDtoToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setReviewHeading(reviewDto.getReviewHeading());
        review.setReviewComment(reviewDto.getReviewComment());
        review.setRating(reviewDto.getRating());
        review.setIsApproved(false);
        return review;
    }
}


