import { Component, OnInit } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ApiService } from 'src/app/services/api/api.service';
import { SharedService } from 'src/app/services/shared/shared.service';
import { Review } from 'src/shared/reviews.interface';

/**
 *  Review component is responsible for displaying the reviews of the product,
 *  it also provides a form to take new reviews and apply validations before
 *  submitting the request to the backend, it displays the reviews in form 
 *  of review tiles.
 */
@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit{
    isLoaded = false;
    reviews: Review[] = [];
    product = {productName: '', brand: '', code: '',productId: ''};
    heading = '';
    comment = '';
    rating = "1";
    headingInValid = false;
    commentInValid = false;

    constructor(private sharedService: SharedService,private apiService: ApiService,private ngxLoader: NgxUiLoaderService){
        this.product.productId = sharedService.getProduct().productId ?? '';
        this.product.productName = sharedService.getProduct().productName ?? '';
        this.product.brand = sharedService.getProduct().brand ?? '';
        this.product.code = sharedService.getProduct().code ?? '';
    }

    ngOnInit(): void {
      this.isLoaded = false;
       this.ngxLoader.start();
       this.apiService.userGetReviews(this.product.productId).subscribe({
        next: (response: any) => {
              this.reviews = response.map((review: any)=>({
                  rating: review.rating,
                  reviewComment: review.review_comment,
                  reviewHeading: review.review_heading,
                  userFirstName: review.user_first_name,
                  userLastName: review.user_last_name,
                  reviewId: review.review_id
             }));
        },
        error: (err: any) => {
              console.log(err);
        }
       })

       setTimeout(() => {
       this.isLoaded = true;
       this.ngxLoader.stop();
       },2000)
       
    }

    isFormInValid(){
      return this.headingInValid && this.commentInValid;
    }

    validateHeading():boolean{
      const headingPattern = /^(?!^\s+$).+/;
      return headingPattern.test(this.heading);
    }

    validateComment():boolean{
      const commentPattern = /^(?!^\s+$).{20,400}/;
      return commentPattern.test(this.comment);
    }

    onSubmit(){
      this.commentInValid = !this.validateComment();
      this.headingInValid = !this.validateHeading();

      if(this.commentInValid || this.headingInValid) return;

      this.apiService.userPostReview(this.rating,this.heading,this.comment,this.product.productId).subscribe({
          next: (response: any) => {
                alert(response.message);
                this.rating = '';
                this.heading = '';
                this.comment = '';
          },
          error: (err: any) => {
                if(err.status == 0)
                alert("Server not responding try again !");
          }
      }
      )
    }
}
