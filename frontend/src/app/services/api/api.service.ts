import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Stats } from 'src/shared/stats.interface';
import { Observable } from 'rxjs';

/**
 *  Api Service provides various method to make request to the different APIs.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  //Sends a get request to fetch the stats from the API
  getStats(): Observable<Stats>{
    return this.http.get<Stats>(environment.statsUrl);
  }

  //Sends a post request for user authentication and retrieve an jwt token
  userLogin(email:string,password:string){
    return this.http.post(environment.userLoginUrl,{email,password});
  }

  //Sends a get request to fetch reviews associated with a product code from the API
  userGetReviews(productCode: any){
      return this.http.get(environment.userGetReviewsUrl + productCode);
  }

  //Sends a post request to register an user
  userRegister(email:string,first_name:string,last_name:string,password:string){
     return this.http.post(environment.userRegisterUrl,{email,first_name,last_name,password});
  }

  //Sends a get request to fetch the products that matches the search query
  productSearch(query: string){
     return this.http.get(environment.productSearchUrl + query);
  }

  //Sends a post request to post a review for a product to API
  userPostReview(rating:string,review_heading:string,review_comment:string,productId:string){
    return this.http.post(environment.userPostReviewUrl + productId,{rating,review_comment,review_heading});
  }

  //Sends a post request to API to make a request for review for a product
  userRequestReview(productName:string,brand:string,code:string){
    return this.http.post(environment.userRequestReviewUrl,{"product_name":productName,"brand":brand,"code":code});
  }

  //Sends a get request to fetch product using the product id from the API
  userGetProduct(productId:string){
    return this.http.get(environment.userGetProductUrl + productId);
  }
  
  //Sends a post request to API to authenticate the admin and retrieve an authentication token
  adminLogin(email:string,password:string){
    return this.http.post(environment.adminLoginUrl,{email,password});
  }
  
  //Sends a get request to fetch the unapproved reviews from the API
  getAdminReviews(){
    return this.http.get(environment.adminGetReviewsUrl);
  }

  //Sends a patch request to the API to approve the review associated with given review id
  approveReview(reviewId: string){
    return this.http.patch(environment.adminApproveReviewUrl+reviewId,{} )
  }

  //Sends a delete request to delete the review associated with given review id to the API
  rejectReview(reviewId: string){
    return this.http.delete(environment.adminApproveReviewUrl+reviewId)
  }

}
