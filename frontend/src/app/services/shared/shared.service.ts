import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

/**
 *  Shared service provides shared functions and data 
 *  accessible to all the components of the application
 */
@Injectable({
  providedIn: 'root',
})
export class SharedService {

  constructor(private router: Router) {}

  /**
   * logout method clears the local storage if isAdminLogout flag is true redirect 
   * to the admin home else to the user home route
   * @param isAdminLogout - boolean flag to imply admin logout
   */
  logout(isAdminLogout: boolean){
     localStorage.clear();
     if(isAdminLogout) this.router.navigate(['admin']);
  }

  /**
   * set search query for the product search in the local storage
   * @param query - search query for the product search
   */
  setSearchQuery(query: string){
     localStorage.setItem('query',query);
  }

  /**
   * returns the search query from the local storage 
   * @returns query saved in the local storage
   */
  getSearchQuery(){
    return localStorage.getItem('query') ?? '';
  }

  /**
   * stores product attributes in the local storage
   * @param productName - Name of the product
   * @param brand - Brand of the product
   * @param code - Code of the product 
   * @param productId - Product id of the product
   */
  setProduct(productName: string, brand: string, code: string, productId: string){
      localStorage.setItem('productName',productName);
      localStorage.setItem('brand',brand);
      localStorage.setItem('code',code);
      localStorage.setItem('productId',productId);
  }

  /**
   * returns the saved product attributes as an object
   * @returns an object of product details
   */
  getProduct(){
    return {productName: localStorage.getItem('productName'), brand: localStorage.getItem('brand'), code: localStorage.getItem('code'), productId: localStorage.getItem('productId')};
  }
}
