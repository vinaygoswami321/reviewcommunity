package com.reviewcommunity.repository;

import com.reviewcommunity.entity.RequestReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestReviewRepository extends JpaRepository<RequestReview, Long> {
}
