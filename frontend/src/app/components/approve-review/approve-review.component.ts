import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ApiService } from 'src/app/services/api/api.service';
import { Review } from 'src/shared/reviews.interface';

/**
 *  Approve review component provides a table for unapproved reviews,
 *  this table displayes unapproved reviews and have an action column
 *  for each unapproved review with approve and reject buttons,
 *  to approve or reject any unapproved review.
 */
@Component({
  selector: 'app-approve-review',
  templateUrl: './approve-review.component.html',
  styleUrls: ['./approve-review.component.css'],
})
export class ApproveReviewComponent implements OnInit {
  isLoaded = false;
  reviews:Review[] = [];

  constructor(
    private router: Router,
    private apiService: ApiService,
    private ngxLoader: NgxUiLoaderService
  ) {}

  ngOnInit(): void {
    this.isLoaded = false;
    this.ngxLoader.start();
    
    this.getReviews();

    setTimeout(() => {
        this.isLoaded = true;
        this.ngxLoader.stop();
    }, 1000);
  }

  approve(reviewId: string){
     this.apiService.approveReview(reviewId).subscribe({
      next: (response: any) => {
        console.log(response);
      },
      error: (err: any) => {
        console.log(err);
      },
     })
     
     setTimeout(() => {
      this.getReviews();
    },1000);
  }

  reject(reviewId: string){
    this.apiService.rejectReview(reviewId).subscribe({
     next: (response: any) => {
       console.log(response);
     },
     error: (err: any) => {
       console.log(err);
     },
    })

    setTimeout(() => {
      this.getReviews();
    },1000);
 }

  getReviews(){
    this.apiService.getAdminReviews().subscribe({
      next: (response: any) => {
        this.reviews = response.map((review: any) => (
          {
            rating: review.rating,
            reviewComment: review.review_comment,
            reviewHeading: review.review_heading,
            userFirstName: review.user_first_name,
            userLastName: review.user_last_name,
            reviewId: review.review_id
          }
        ))
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }
}
