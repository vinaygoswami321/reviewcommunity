import { Component, OnInit } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { Router } from '@angular/router';
import { Product } from 'src/shared/products.interface';
import { SharedService } from 'src/app/services/shared/shared.service';
import { ApiService } from 'src/app/services/api/api.service';

/**
 *  The product search component handles the product search
 *  and displays the results of the search in form of product tiles
 *  it also provide filters to further filter the search results
 */
@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css'],
})
export class ProductSearchComponent implements OnInit {
  searchQuery = '';
  products: Product[] = [];
  filteredProducts: Product[] = [];
  productNames: string[] = [];
  brands: string[] = [];
  codes: string[] = [];
  selectedProductName: string = '';
  selectedBrand: string = '';
  selectedCode: string = '';
  isLoaded = false;
  errorFlag = false;

  constructor(
    private sharedService: SharedService,
    private apiService: ApiService,
    private ngxLoader: NgxUiLoaderService,
    private router: Router
  ) {
    this.searchQuery = sharedService.getSearchQuery();
  }

  ngOnInit() {
    this.search();
  }

  updateSearchQuery(){
    this.isLoaded = false;
    this.sharedService.setSearchQuery(this.searchQuery);
    this.errorFlag = false;
    this.search();
  }

  search(){
    this.ngxLoader.start();
    this.selectedBrand = '';
    this.selectedProductName = '';
    this.selectedCode = '';
    
    this.apiService.productSearch(this.searchQuery).subscribe({
      next: (response: any) => {
          this.products = response.map((productData: any) => ({
                averageRating : productData.average_rating,
                brand: productData.brand,
                code: productData.code,
                numberOfReviews: productData.number_of_reviews,
                productId: productData.product_id,
                productName: productData.product_name
            })
          )
          
          if(this.products.length == 0) this.errorFlag = true;
         
          this.filteredProducts = this.products;
          this.populateFilters(this.products);
          
      },
      error: (err: any) => {
        console.log(err);
      },
    });

    setTimeout(() => {
      this.isLoaded = true;
      this.ngxLoader.stop();
    }, 2000);
    
  }

  reviews(productName: string,brand: string,code: string,productId: any){
    this.sharedService.setProduct(productName,brand,code,productId);
    this.router.navigateByUrl('/reviews');
  }

  populateFilters(products:Product[]){
     this.productNames = Array.from(new Set(this.products.map((product) => product.productName)));
     this.brands = Array.from(new Set(this.products.map((product) => product.brand)));
     this.codes = Array.from(new Set(this.products.map((product) => product.code)));
  }

  filter(){
    this.filteredProducts = this.products.filter(product => {
      const productNameMatch = this.matcher(product.productName,this.selectedProductName);
      const brandMatch = this.matcher(product.brand,this.selectedBrand);
      const codeMatch = this.matcher(product.code,this.selectedCode);

      if(this.selectedProductName != '' && !productNameMatch){
        return false;
      }
      if(this.selectedBrand != '' && !brandMatch){
        return false;
      }
      if(this.selectedCode!= '' && !codeMatch){
        return false;
      }

      return true;
      }
    )
    
    // console.log(this.selectedBrand,this.selectedCode,this.selectedProductName)
    // console.log(this.filteredProducts);
  }

  matcher(key: string, filter: string): boolean{
    // console.log("matcher",key,filter)
    if(filter == '') return false;
    return key.toLowerCase().includes(filter.toLowerCase());
  }
}
