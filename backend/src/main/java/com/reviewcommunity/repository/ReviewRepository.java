package com.reviewcommunity.repository;

import com.reviewcommunity.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductProductIdAndIsApprovedTrue(long productId);

    List<Review> findByIsApprovedFalse();

    @Query("SELECT COUNT(R) FROM Review R where is_approved = 1")
    int getReviewCount();

    @Query("SELECT COUNT(R) FROM Review R WHERE R.product.productId = :productId and is_approved = 1")
    Long getReviewCountByProductId(@Param("productId") long productId);

    @Query("SELECT ROUND(AVG(R.rating),1) FROM Review R WHERE R.product.productId = :productId and is_approved = 1")
    Double getAverageRatingByProductId(@Param("productId") long productId);
}
