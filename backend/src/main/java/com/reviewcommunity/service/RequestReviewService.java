package com.reviewcommunity.service;

import com.reviewcommunity.dto.RequestReviewDto;
import com.reviewcommunity.exception.ReviewException;

public interface RequestReviewService {
    void addRequest(RequestReviewDto requestReviewDto) throws ReviewException;
}
