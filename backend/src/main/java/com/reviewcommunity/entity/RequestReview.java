package com.reviewcommunity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request_review_table")
public class RequestReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestReviewId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String code;

}
