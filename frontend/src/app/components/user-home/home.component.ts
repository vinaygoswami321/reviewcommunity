import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api/api.service';
import { Stats } from 'src/shared/stats.interface';
import { map } from 'rxjs';
import { SharedService } from '../../services/shared/shared.service';
import { AuthService } from 'src/app/services/auth/auth.service';

/**
 *  Home component is the main page of the application
 *  it displays the stats of the application and
 *  for authenticated user it also displays a search bar
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class UserHomeComponent implements OnInit {
  isActive = false;
  stats: Stats = {
    userCount: 0,
    productCount: 0,
    reviewCount: 0,
  };
  searchQuery = '';

  constructor(private router: Router, private apiService: ApiService, private sharedService: SharedService,private authService: AuthService) {}

  ngOnInit(): void {
    this.apiService.getStats().subscribe({
      next: (response: any) => {
        this.stats = {
          userCount: response.Stats.user_count,
          productCount: response.Stats.product_count,
          reviewCount: response.Stats.review_count
        }
      },
      error: (err: any) => {
        console.log(err);
      },
    });
    
    setTimeout(() => {
      this.isActive = true;
    },500)
  }

  isUserLoggedIn(){
    return this.authService.isUserLoggedIn();
  }

  updateSearchQuery(){
    this.sharedService.setSearchQuery(this.searchQuery);
  }
}