package com.reviewcommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private long reviewId;

    private String reviewHeading;

    private String reviewComment;

    private int rating;

    private long userId;

    private String userFirstName;

    private String userLastName;
}
