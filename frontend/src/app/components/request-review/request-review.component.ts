import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api/api.service';
import { SharedService } from 'src/app/services/shared/shared.service';

/**
 *  The request review component provides a form to take input from user 
 *  for requeting review for a product and apply validation on them 
 *  before submitting the request to the backend, it also handles 
 *  displaying errors on the screen.
 */
@Component({
  selector: 'app-request-review',
  templateUrl: './request-review.component.html',
  styleUrls: ['./request-review.component.css']
})
export class RequestReviewComponent {
    productName = '';
    brand = '';
    code = '';
    errorFlag = false;
    successFlag = false;
    productNameInValid = false;
    brandInValid = false;
    codeInValid = false;
    errorMessage = "";

    constructor(private router: Router,private apiService: ApiService,private sharedService: SharedService){}

    onSubmit(){
        this.errorFlag = false;
        this.productNameInValid = this.invalidDetail(this.productName);
        this.brandInValid = this.invalidDetail(this.brand);
        this.codeInValid = this.invalidDetail(this.code);

        if(this.productNameInValid || this.brandInValid || this.codeInValid){
          this.errorFlag = true;
        }

        this.apiService.userRequestReview(this.productName,this.brand,this.code).subscribe({
          next: (response: any) => {
            this.successFlag = true;
          },
          error: (err: any) => {
            this.errorFlag = true;
            
            if(err.status == 409){
              this.errorMessage = err.error.message; 
              this.apiService.userGetProduct(this.code).subscribe({
                next: (response: any) => {
                  this.sharedService.setProduct(response.product_name,response.brand,response.code,response.product_id);
                  setTimeout(() => {
                    this.router.navigateByUrl("/reviews");
                  },30000)
                },
                error: (err: any) => {
                  console.log(err);
                }
              })
            }
            else if(err.status == 0){
              this.errorMessage = "Server not responding try again";
            }
          }
        })
    }

    invalidDetail(field : string):boolean{
      return field == '';
    }
}
