package com.reviewcommunity.controller;

import com.reviewcommunity.dto.RequestReviewDto;
import com.reviewcommunity.dto.ReviewDto;
import com.reviewcommunity.exception.ReviewException;
import com.reviewcommunity.service.impl.RequestReviewServiceImpl;
import com.reviewcommunity.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewService;


    @Autowired
    private RequestReviewServiceImpl requestReviewService;

    /*
    *   Post review for user
    *   by taking product id as a path variable
    *   and review heading, review comment, rating in the request body
    * */
    @PostMapping("/api/user/post/review/{productId}")
    public ResponseEntity<?> postReview(@PathVariable("productId") long productId, @RequestBody ReviewDto reviewDto, HttpServletRequest request) {
        Map<String, String> responseBody = new HashMap<>();

        try {
            reviewService.addReview(reviewDto, productId, request);
            responseBody.put("message", "Review posted successfully");
        } catch (ReviewException e) {
            responseBody.put("message", "Error in posting review");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    /*
    *   Retrieves a list of approved reviews
    *   for user by taking product id of the product
    * */
    @GetMapping("/api/user/get/reviews/{productId}")
    public ResponseEntity<?> getReviews(@PathVariable("productId") long productId, HttpServletRequest request) {
        List<ReviewDto> approvedReviewDtoList = reviewService.getApprovedReviews(productId);
        return new ResponseEntity<>(approvedReviewDtoList, HttpStatus.OK);
    }

    /*
    *   Retrieves a list of un approved reviews
    *   of all the users for the admin
    * */
    @GetMapping("/api/admin/get/reviews")
    public ResponseEntity<?> getUnApprovedReviews() {
        List<ReviewDto> unApprovedReviewDtoList = reviewService.getUnApprovedReviews();

        return new ResponseEntity<>(unApprovedReviewDtoList, HttpStatus.OK);
    }

    /*
    *   Creates a review request for a product by user
    *   taking product code, product name, brand in the response body
    *   if product code doesn't exist in the product table
    *
    *   If product code already exists in the product table return a
    *   conflict status code in the response
    * */
    @PostMapping("/api/user/request/review")
    public ResponseEntity<?> requestReview(@RequestBody RequestReviewDto requestReviewDto) {
        Map<String, String> responseBody = new HashMap<>();

        try {
            requestReviewService.addRequest(requestReviewDto);
            responseBody.put("message", "Review requested successfully");
        } catch (ReviewException e) {
            if (e.getMessage().equals("Error in requesting review")) {
                responseBody.put("message", e.getMessage());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals("Product already exists")) {
                responseBody.put("message", e.getMessage());
                return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
            }
        }

        responseBody.put("message", "Request for review added successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    /*
    *   Approve an un approved review posted by a user
    *   taking review id as a path variable if review exists in the
    *   review table otherwise return a not found status code in response
    * */
    @PatchMapping("/api/admin/approve/review/{reviewId}")
    public ResponseEntity<?> approveReview(@PathVariable("reviewId") long reviewId) {
        Map<String, String> responseBody = new HashMap<>();

        try {
            reviewService.approveReview(reviewId);
            responseBody.put("message", "Review approved");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (ReviewException e) {
            responseBody.put("message", e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    /*
    *   Reject an un approved review posted by a user
    *   taking review id as a path variable if the review
    *   exists in the review table otherwise return error status
    *   code in the response
    * */
    @DeleteMapping("/api/admin/reject/review/{reviewId}")
    public ResponseEntity<?> rejectReview(@PathVariable("reviewId") long reviewId) {
        Map<String, String> responseBody = new HashMap<>();

        try {
            reviewService.rejectReview(reviewId);
        } catch (ReviewException e) {
            responseBody.put("message", "Error in rejecting review " + e.getMessage());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        responseBody.put("message", "Review rejected successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
